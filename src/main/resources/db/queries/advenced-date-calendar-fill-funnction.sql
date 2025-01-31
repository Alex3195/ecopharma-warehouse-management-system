CREATE OR REPLACE FUNCTION populate_date_calendar(
    table_name TEXT,
    start_date DATE,
    end_date DATE
) RETURNS VOID LANGUAGE plpgsql AS $$
BEGIN
EXECUTE format('
        INSERT INTO %I (
            date_id, date, epoch, day_suffix, day_name, day_name_abbr, day_of_week, day_of_month,
            day_of_quarter, day_of_year, week_of_month, week_of_year, week_of_year_iso, month,
            month_name, month_name_abbr, quarter, quarter_name, year, start_of_week, start_of_week_monday,
            start_of_week_saturday, start_of_month, start_of_mid_month, start_of_quarter, start_of_year,
            end_of_week, end_of_month, end_of_quarter, end_of_year, start_of_fiscal_year, end_of_fiscal_year,
            date_format_yyyymm, date_format_yyyymmdd, month_description, quarter_description, week_description,
            is_weekend, season, start_of_season, moon_illumination, days_in_a_month, is_laeop_year
        )
        WITH date_range AS (
            SELECT date
            FROM generate_series(%L::DATE, %L::DATE, INTERVAL ''1 day'') AS date
        )
        SELECT
            CAST(TO_CHAR(date, ''YYYYMMDD'') AS INTEGER) AS date_id,
            date AS date,
            EXTRACT(EPOCH FROM date)::INTEGER AS epoch,
            CASE
                WHEN EXTRACT(DAY FROM date) IN (1, 21, 31) THEN EXTRACT(DAY FROM date)::TEXT || ''st''
                WHEN EXTRACT(DAY FROM date) IN (2, 22) THEN EXTRACT(DAY FROM date)::TEXT || ''nd''
                WHEN EXTRACT(DAY FROM date) IN (3, 23) THEN EXTRACT(DAY FROM date)::TEXT || ''rd''
                ELSE EXTRACT(DAY FROM date)::TEXT || ''th''
            END AS day_suffix,
            TO_CHAR(date, ''Day'') AS day_name,
            TO_CHAR(date, ''Dy'') AS day_name_abbr,
            EXTRACT(ISODOW FROM date) AS day_of_week,
            EXTRACT(DAY FROM date) AS day_of_month,
            EXTRACT(DAY FROM date) - EXTRACT(DAY FROM DATE_TRUNC(''quarter'', date)) + 1 AS day_of_quarter,
            EXTRACT(DOY FROM date) AS day_of_year,
            EXTRACT(WEEK FROM date) - EXTRACT(WEEK FROM DATE_TRUNC(''month'', date)) + 1 AS week_of_month,
            EXTRACT(WEEK FROM date) AS week_of_year,
            TO_CHAR(date, ''IYYY-IW-ID'') AS week_of_year_iso,
            EXTRACT(MONTH FROM date) AS month,
            TO_CHAR(date, ''Month'') AS month_name,
            TO_CHAR(date, ''Mon'') AS month_name_abbr,
            EXTRACT(QUARTER FROM date) AS quarter,
            ''Q'' || EXTRACT(QUARTER FROM date)::TEXT AS quarter_name,
            EXTRACT(YEAR FROM date) AS year,
            date_trunc(''week'', date) AS start_of_week,
            date_trunc(''week'', date) + INTERVAL ''1 day'' AS start_of_week_monday,
            date_trunc(''week'', date) - INTERVAL ''1 day'' AS start_of_week_saturday,
            date_trunc(''month'', date) AS start_of_month,
            date_trunc(''month'', date) + INTERVAL ''14 days'' AS start_of_mid_month,
            date_trunc(''quarter'', date) AS start_of_quarter,
            date_trunc(''year'', date) AS start_of_year,
            date_trunc(''week'', date) + INTERVAL ''6 days'' AS end_of_week,
            date_trunc(''month'', date) + INTERVAL ''1 month - 1 day'' AS end_of_month,
            date_trunc(''quarter'', date) + INTERVAL ''3 months - 1 day'' AS end_of_quarter,
            date_trunc(''year'', date) + INTERVAL ''1 year - 1 day'' AS end_of_year,
            CASE
                WHEN EXTRACT(MONTH FROM date) >= 10 THEN
                    make_date(EXTRACT(YEAR FROM date)::INTEGER, 10, 1)
                ELSE
                    make_date(EXTRACT(YEAR FROM date)::INTEGER - 1, 10, 1)
            END AS start_of_fiscal_year,
            CASE
                WHEN EXTRACT(MONTH FROM date) >= 10 THEN
                    make_date(EXTRACT(YEAR FROM date)::INTEGER + 1, 9, 30)
                ELSE
                    make_date(EXTRACT(YEAR FROM date)::INTEGER, 9, 30)
            END AS end_of_fiscal_year,
            TO_CHAR(date, ''YYYYMM'') AS date_format_yyyymm,
            TO_CHAR(date, ''YYYYMMDD'') AS date_format_yyyymmdd,
            TO_CHAR(date, ''YYYY-Mon'') AS month_description,
            ''Q'' || EXTRACT(QUARTER FROM date)::TEXT AS quarter_description,
            ''W'' || TO_CHAR(date, ''IW'') AS week_description,
            CASE WHEN EXTRACT(ISODOW FROM date) IN (6, 7) THEN TRUE ELSE FALSE END AS is_weekend,
            CASE
                WHEN EXTRACT(MONTH FROM date) IN (12, 1, 2) THEN ''Winter''
                WHEN EXTRACT(MONTH FROM date) IN (3, 4, 5) THEN ''Spring''
                WHEN EXTRACT(MONTH FROM date) IN (6, 7, 8) THEN ''Summer''
                WHEN EXTRACT(MONTH FROM date) IN (9, 10, 11) THEN ''Autumn''
            END AS season,
            CASE
                WHEN EXTRACT(MONTH FROM date) IN (12, 1, 2) THEN make_date(EXTRACT(YEAR FROM date)::INTEGER, 12, 21)
                WHEN EXTRACT(MONTH FROM date) IN (3, 4, 5) THEN make_date(EXTRACT(YEAR FROM date)::INTEGER, 3, 21)
                WHEN EXTRACT(MONTH FROM date) IN (6, 7, 8) THEN make_date(EXTRACT(YEAR FROM date)::INTEGER, 6, 21)
                WHEN EXTRACT(MONTH FROM date) IN (9, 10, 11) THEN make_date(EXTRACT(YEAR FROM date)::INTEGER, 9, 21)
            END AS start_of_season,
            ABS(COS(RADIANS(MOD(EXTRACT(EPOCH FROM date) - EXTRACT(EPOCH FROM ''2000-01-06''::TIMESTAMP), 86400 * 29.530588) / (86400 * 29.530588) * 360))) AS moon_illumination,
            EXTRACT(DAY FROM date_trunc(''month'', date) + INTERVAL ''1 month - 1 day'') AS days_in_a_month,
            CASE
                WHEN EXTRACT(YEAR FROM date) %% 4 = 0 AND (EXTRACT(YEAR FROM date) %% 100 != 0 OR EXTRACT(YEAR FROM date) %% 400 = 0) THEN 1
                ELSE 0
            END AS is_laeop_year
        FROM date_range;
    ', table_name, start_date, end_date);
END $$;
