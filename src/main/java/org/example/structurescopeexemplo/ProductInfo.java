package org.example.structurescopeexemplo;

import java.util.List;

public record ProductInfo(Product product, List<Review> reviews) { }
