package com.example.movieticket.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CardHandler {
    private static final String CARD_FILE_PATH = "src/main/resources/data/cardpayments.txt";

    public synchronized List<String> readAllCards() {
        List<String> cards = new ArrayList<>();
        try {
            File file = new File(CARD_FILE_PATH);
            if (!file.exists()) {
                return cards;
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    cards.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public synchronized String readCardByCardId(String cardId) {
        List<String> cards = readAllCards();
        for (String card : cards) {
            if (card.startsWith(cardId + "|")) {
                return card;
            }
        }
        return null;
    }

    public synchronized List<String> readCardsByUserId(String userId) {
        List<String> userCards = new ArrayList<>();
        List<String> allCards = readAllCards();
        for (String card : allCards) {
            String[] parts = card.split("\\|");
            if (parts.length >= 2 && parts[1].equals(userId)) {
                userCards.add(card);
            }
        }
        return userCards;
    }

    public synchronized void createCard(String cardData) {
        try {
            FileWriter fw = new FileWriter(CARD_FILE_PATH, true);
            fw.write(cardData + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateCard(String cardId, String updatedCardData) {
        List<String> cards = readAllCards();
        try {
            FileWriter fw = new FileWriter(CARD_FILE_PATH, false);
            for (String card : cards) {
                if (!card.startsWith(cardId + "|")) {
                    fw.write(card + "\n");
                } else {
                    fw.write(updatedCardData + "\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteCard(String cardId) {
        List<String> cards = readAllCards();
        try {
            FileWriter fw = new FileWriter(CARD_FILE_PATH, false);
            for (String card : cards) {
                if (!card.startsWith(cardId + "|")) {
                    fw.write(card + "\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getNextCardId() {
        List<String> cards = readAllCards();
        if (cards.isEmpty()) {
            return "C001";
        }
        String lastCard = cards.get(cards.size() - 1);
        String lastCardId = lastCard.split("\\|")[0];
        int id = Integer.parseInt(lastCardId.substring(1)) + 1;
        return "C" + String.format("%03d", id);
    }
}
