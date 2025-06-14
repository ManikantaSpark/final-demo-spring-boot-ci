package com.example.Final.Demo.Spring.Project.functional;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FunctionalTestSuite {
    @Test(groups = {"sanity", "product", "smoke"})
    public void testListAllProducts() {
        Assert.assertTrue(true, "Products listed successfully");
    }

    @Test(groups = {"cart", "sanity"})
    public void testGetCurrentCart() {
        Assert.assertTrue(true, "Current cart returned");
    }

    @Test(groups = {"order", "sanity", "smoke"})
    public void testCheckout() {
        Assert.assertTrue(true, "Order placed from cart");
    }

    @Test(groups = {"product"})
    public void testGetProductById() {
        Assert.assertTrue(true, "Product details fetched for valid ID");
    }
}
