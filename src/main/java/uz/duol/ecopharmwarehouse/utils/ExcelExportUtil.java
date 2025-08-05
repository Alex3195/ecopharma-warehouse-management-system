package uz.duol.ecopharmwarehouse.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil {

    public static <T> byte[] exportToExcel(List<T> data, List<String> fieldNames, List<String> columnNames, String sheetName) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Empty data");
        }
        if (fieldNames == null || columnNames == null || fieldNames.size() != columnNames.size()) {
            throw new IllegalArgumentException("Field names and column names must match in size");
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(sheetName != null ? sheetName : "Sheet1");

            // Build header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnNames.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnNames.get(i));
            }

            // Cache fields by name for faster access
            Class<?> clazz = data.getFirst().getClass();
            Map<String, Field> fieldMap = new HashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                fieldMap.put(field.getName(), field);
            }

            // Fill data rows
            for (int i = 0; i < data.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                T item = data.get(i);

                for (int j = 0; j < fieldNames.size(); j++) {
                    String fieldName = fieldNames.get(j);
                    Cell cell = dataRow.createCell(j);

                    Field field = fieldMap.get(fieldName);
                    if (field != null) {
                        Object value = field.get(item);
                        cell.setCellValue(value != null ? value.toString() : "");
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException("Failed to export Excel", e);
        }
    }
}
