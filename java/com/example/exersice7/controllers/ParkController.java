package com.example.exersice7.controllers;

import com.example.exersice7.model.ApiResponse;
import com.example.exersice7.model.Park;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/park")
public class ParkController {

    ArrayList<Park> rides = new ArrayList<Park>();

    @GetMapping
    public ResponseEntity getRides() {
        return ResponseEntity.status(200).body(rides);
    }

    @PostMapping
    public ResponseEntity addRides(@RequestBody @Valid Park ride,
                                   Errors errors) {
        return getResponseEntity(ride, errors);
    }

    @PutMapping
    public ResponseEntity updateRides(@RequestBody @Valid Park ride, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            ApiResponse apiResponse = new ApiResponse(message, 400);
            return ResponseEntity.status(400).body(apiResponse);
        }

        for (int i = 0; i < rides.size(); i++) {
            if ((rides.get(i).getRideID()).equals(ride.getRideID()) ){
                rides.add(i,ride);
                return ResponseEntity.status(200).body(new ApiResponse("The ride is updated", 200));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("The index is not exist", 400));
    }

    private ResponseEntity getResponseEntity(@RequestBody @Valid Park ride, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            ApiResponse apiResponse = new ApiResponse(message, 400);
            return ResponseEntity.status(400).body(apiResponse);
        }
        rides.add(ride);
        return ResponseEntity.status(200).body(rides);
    }




    @DeleteMapping("/{index}")
    public ResponseEntity deleteRides(@PathVariable  @Valid Integer index,
                                      Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            ApiResponse apiResponse = new ApiResponse(message, 400);
            return ResponseEntity.status(400).body(apiResponse);
        }

                   if (index < rides.size()|| index >= 0) {

                       rides.remove((int) index);
                       return ResponseEntity.status(200).body(new ApiResponse("The ride is deleted", 200));
                   }

        return ResponseEntity.status(400).body(new ApiResponse("The index is not exist", 400));
    }

    @GetMapping("/{id}/{amount}")
    public ResponseEntity sellRide(@PathVariable @Valid Integer id,
                                   @PathVariable @Valid Integer amount ) {
        for (int i = 0; i < rides.size(); i++) {
            if (rides.get(i).getRideID() == id) {

                if (rides.get(i).getTickets() == 0)
                    return ResponseEntity.status(400).body(new ApiResponse("The ticket is not available 0", 400));
                if (amount < rides.get(i).getPrice())
                    return ResponseEntity.status(400).body(new ApiResponse("Your price not enough", 400));

                Integer newTicket = (rides.get(i).getTickets()) - 1;
                rides.get(i).setTickets(newTicket);
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse("The sell is done", 200));
    }
}
