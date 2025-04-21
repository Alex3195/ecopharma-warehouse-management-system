package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.duol.ecopharmwarehouse.module.store.dto.StoreSyncRequest;
import uz.duol.ecopharmwarehouse.module.store.service.StoreAggregationService;

@RestController
@RequestMapping("/api/v1/wms/store-aggregation")
@RequiredArgsConstructor
public class StoreAggregationController {
    private final StoreAggregationService storeAggregationService;

    @PostMapping("/store-sync")
    public String storeSync(@RequestBody StoreSyncRequest request) {
        return storeAggregationService.createAndReturnBarCode(request);

    }
}
