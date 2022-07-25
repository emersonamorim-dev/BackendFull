package com.backend.backendfull.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backendfull.services.GameService;
import com.backend.backendfull.shared.GameDTO;
import com.backend.backendfull.view.model.GameRequest;
import com.backend.backendfull.view.model.GameResponse;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameResponse>> getAll() {
        List<GameDTO> games = gameService.getAll();

        ModelMapper mapper = new ModelMapper();

        List<GameResponse> resposta = games.stream()
                .map(gameDto -> mapper.map(gameDto, GameResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<GameResponse>> getId(@PathVariable Integer id) {

        // try {
        Optional<GameDTO> dto = gameService.getId(id);

        GameResponse game = new ModelMapper().map(dto.get(), GameResponse.class);

        return new ResponseEntity<>(Optional.of(game), HttpStatus.OK);
        /*
         * } catch (Exception e) {
         * return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         * }
         */

    }

    @PostMapping
    public ResponseEntity<GameResponse> add(@RequestBody GameRequest gameReq) {
        ModelMapper mapper = new ModelMapper();
        GameDTO gameDto = mapper.map(gameReq, GameDTO.class);

        gameDto = gameService.add(gameDto);

        return new ResponseEntity<>(mapper.map(gameDto, GameResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        gameService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> update(@RequestBody GameRequest gameReq, @PathVariable Integer id) {
        ModelMapper mapper = new ModelMapper();
        GameDTO gameDto = mapper.map(gameReq, GameDTO.class);

        gameDto = gameService.update(id, gameDto);

        return new ResponseEntity<>(
                mapper.map(gameDto, GameResponse.class),
                HttpStatus.OK);
    }
}