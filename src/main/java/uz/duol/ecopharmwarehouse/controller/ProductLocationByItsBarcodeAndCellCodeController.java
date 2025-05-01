package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.product.location.dto.ProductLocationByItsBarcodeAndLocationCodeDto;
import uz.duol.ecopharmwarehouse.module.product.location.service.ProductLocationByItsBarcodeAndCellCodeService;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','USER','SUPER_ADMIN')")
@RequestMapping("/api/v1/wms/product-location")
public class ProductLocationByItsBarcodeAndCellCodeController {
    private final ProductLocationByItsBarcodeAndCellCodeService service;

    @PostMapping
    public ProductLocationByItsBarcodeAndLocationCodeDto createProductLocation(@RequestBody ProductLocationByItsBarcodeAndLocationCodeDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{barcode}")
    public String findProductLocationByItsBarcode(@PathVariable String barcode) {
        return service.findByProductBarCodeItsLocationCode(barcode);
    }

    @PutMapping("/{barcode}/{locationCode}")
    public ProductLocationByItsBarcodeAndLocationCodeDto updateProductLocation(@PathVariable String barcode, @PathVariable String locationCode) {
        return service.update(barcode, locationCode);
    }

    @GetMapping("/list")
    public Page<ProductLocationByItsBarcodeAndLocationCodeDto> getProductLocationByItsBarcode(@RequestParam(value = "search", required = false) String search,
                                                                                              @PageableDefault Pageable pageable) {
        return service.getProductLocationByItsBarcode(search, pageable);
    }
}
