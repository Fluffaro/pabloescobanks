package com.java.pabloescobanks.controller;

import com.java.pabloescobanks.entity.Request;
import com.java.pabloescobanks.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // 🟢 Create a new money request
    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(
            @RequestParam Long requesterAccId,
            @RequestParam Long receiverAccId,
            @RequestParam Double amount) {
        Request request = requestService.createRequest(requesterAccId, receiverAccId, amount);
        return ResponseEntity.ok(request);
    }

    // 🟢 Fetch all requests where the user is the receiver
    @GetMapping("/receiver/{receiverAccId}")
    public ResponseEntity<List<Request>> getRequestsForReceiver(@PathVariable Long receiverAccId) {
        return ResponseEntity.ok(requestService.getRequestsForReceiver(receiverAccId));
    }

    // 🟢 Fetch all requests where the user is the requester
    @GetMapping("/requester/{requesterAccId}")
    public ResponseEntity<List<Request>> getRequestsForRequester(@PathVariable Long requesterAccId) {
        return ResponseEntity.ok(requestService.getRequestsForRequester(requesterAccId));
    }

    // ✅ Update request status: confirm or cancel
    @PutMapping("/update/{requestId}")
    public ResponseEntity<String> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestParam String statusRequest) {
        return ResponseEntity.ok(requestService.updateRequestStatus(requestId, statusRequest));
    }


}
