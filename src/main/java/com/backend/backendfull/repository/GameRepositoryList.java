package com.backend.backendfull.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.backend.backendfull.model.Game;
import com.backend.backendfull.model.exception.ResourceNotFoundException;

@Repository
public class GameRepositoryList {

    private List<Game> games = new ArrayList<Game>();
    private Integer finalId = 0;

    /**
     * Return product list
     * 
     * @return
     */
    public List<Game> getAll() {
        return games;
    }

    /**
     * Method that returns the product by ID
     * 
     * @param id
     * @return returns a product
     */
    public Optional<Game> getId(Integer id) {
        return games
                .stream()
                .filter(game -> game.getId() == id)
                .findFirst();

    }

    /**
     * Method to add product to the list.
     * 
     * @param game that will be added
     * @return returns the product that was added to the list
     */
    public Game add(Game game) {

        finalId++;
        game.setId(finalId);
        games.add(game);

        return game;
    }

    /**
     * Method delete product by ID
     * 
     * @param id
     */
    public void delete(Integer id) {
        games.removeIf(game -> game.getId() == id);
    }

    /**
     * Method to update the product in the list
     * 
     * @param game
     * @return
     */
    public Game update(Game game) {

        // Find a product in the list
        Optional<Game> gameEncontrado = getId(game.getId());

        if (gameEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Game não pode ser atualizado. Pois não existe!");
        }

        // delete product in the list
        delete(game.getId());

        // updated product in the list
        games.add(game);

        return game;
    }

}
