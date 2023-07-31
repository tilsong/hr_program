package com.hr_program.api.service.AnniversaryApI;

import com.hr_program.api.intergration.AnniversaryApiClient;
import com.hr_program.api.service.AnniversaryApI.response.AnniversaryInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnniversaryApiService {

    private final AnniversaryApiClient apiClient;

    public List<AnniversaryInfoResponse> getAnniversaryInfo(String year, String month) {
         return apiClient.getAnniversaryInfo(year, month)
                .stream()
                .map(AnniversaryInfoResponse::from)
                .collect(Collectors.toList());
    }
}
