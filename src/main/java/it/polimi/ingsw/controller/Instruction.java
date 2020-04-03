package it.polimi.ingsw.controller;

/**
 * the enumeration of all the instructions that client and server can send each others
 */
public enum Instruction {
    move, buildBlock, buildDome, setCard, setPlayerID, endGame, initialCardChoose, initialPosition, resumeGame, setName;
}