package org.example.c06.model;


import org.example.c06.enumeration.AlertType;

public record PriceAlert(String symbol, String message, AlertType type) {
}
