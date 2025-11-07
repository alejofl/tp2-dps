package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.model.Client;
import com.rt.springboot.app.port.driving.client.DeleteClientUseCase;
import com.rt.springboot.app.port.driving.client.FindAllClientsUseCase;
import com.rt.springboot.app.port.driving.client.FindClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientDrivingAdapter {

    private final FindAllClientsUseCase findAllClientsUseCase;
    private final FindClientUseCase findClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;

    @GetMapping()
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(findAllClientsUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable UUID id) {
        return ResponseEntity.ok(findClientUseCase.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        deleteClientUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
