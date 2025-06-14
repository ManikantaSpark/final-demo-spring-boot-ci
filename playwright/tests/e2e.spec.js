// playwright/tests/e2e.spec.js
const { test, expect } = require('@playwright/test');

const BASE_URL = 'http://localhost:8080/index.html';

test.describe('E-commerce Store Functional Flows', () => {
  test('Product grid loads and displays products', async ({ page }) => {
    await page.goto(BASE_URL);
    await expect(page.locator('.products-grid .product-card')).toHaveCountGreaterThan(0);
  });

  test('Filter products by category and availability', async ({ page }) => {
    await page.goto(BASE_URL);
    await page.selectOption('#categoryFilter', 'Electronics');
    await page.selectOption('#availabilityFilter', 'true');
    await page.click('button:has-text("Apply Filters")');
    await expect(
      page.locator('.products-grid').locator('text=Electronics')
    ).toBeVisible();
  });

  test('Add a product to the cart', async ({ page }) => {
    await page.goto(BASE_URL);
    const firstQtyInput = page.locator('.quantity-input').first();
    await firstQtyInput.fill('2');
    await page.locator('.add-to-cart:not([disabled])').first().click();
    await page.waitForTimeout(500); // Wait for cart update
    await expect(page.locator('#cartContainer')).toContainText('Quantity: 2');
  });

  test('Cart displays correct items and totals', async ({ page }) => {
    await page.goto(BASE_URL);
    await expect(page.locator('#cartContainer')).toContainText('Quantity:');
    await expect(page.locator('#cartContainer')).toContainText('Total Items:');
    await expect(page.locator('#cartContainer')).toContainText('Total Price:');
  });

  test('Remove an item from the cart', async ({ page }) => {
    await page.goto(BASE_URL);
    // Add an item first
    await page.locator('.add-to-cart:not([disabled])').first().click();
    await page.waitForTimeout(500);
    // Remove
    const removeBtn = page.locator('button', { hasText: 'Remove' }).first();
    if (await removeBtn.isVisible()) {
      await removeBtn.click();
      await page.waitForTimeout(500);
      await expect(page.locator('#cartContainer')).toContainText('Your cart is empty');
    }
  });

  test('Proceed to checkout and empty cart', async ({ page }) => {
    await page.goto(BASE_URL);
    // Add an item
    await page.locator('.add-to-cart:not([disabled])').first().click();
    await page.waitForTimeout(500);
    await page.click('.checkout-btn');
    // Handle browser prompts
    page.once('dialog', async dialog => {
      await dialog.accept('123 Main St');
    });
    page.once('dialog', async dialog => {
      await dialog.accept('Credit Card');
    });
    // Accept the result alert
    page.once('dialog', async dialog => {
      await dialog.accept();
    });
    await page.waitForTimeout(1000);
    await expect(page.locator('#cartContainer')).toContainText('Your cart is empty');
  });

  test('Error handling for empty cart checkout', async ({ page }) => {
    await page.goto(BASE_URL);
    await page.click('.checkout-btn');
    // Accept the alert
    page.once('dialog', async dialog => {
      await dialog.accept();
    });
    await page.waitForTimeout(500);
  });

  test('Clear filters resets product list', async ({ page }) => {
    await page.goto(BASE_URL);
    await page.click('button:has-text("Clear Filters")');
    await expect(page.locator('.product-card')).toHaveCountGreaterThan(0);
  });
});
