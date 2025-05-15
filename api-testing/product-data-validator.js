// ChatGPT prompt
//Let's say you're a SDET and you need to develop automated tests to validate data provided by a public API to detect errors and anomalies.
//Initial Data:
//A GET request to https://fakestoreapi.com/products returns an array of objects representing products. The provided JSON data contains defects that need to be identified.
//Test Objectives:
//Verify server response code (expected 200).
//Confirm the presence of the following attributes for each product:
//title (name) - must not be empty.
//price (price) - must not be negative.
//rating.rate -  must not exceed 5.
//Generate a list of products containing defects.
//I want you to implement it via JavaScript.  Also, make a note on how I can run your tests.

const axios = require('axios');

const API_URL = 'https://fakestoreapi.com/products';

(async () => {
  try {
    const response = await axios.get(API_URL);

    // 1. Verify status code
    if (response.status !== 200) {
      console.error(`❌ Expected status 200 but got ${response.status}`);
      return;
    }

    const products = response.data;
    const defectiveProducts = [];

    products.forEach((product, index) => {
      const errors = [];

      if (!product.title || product.title.trim() === '') {
        errors.push('Empty title');
      }

      if (typeof product.price !== 'number' || product.price < 0) {
        errors.push('Negative or invalid price');
      }

      if (!product.rating || typeof product.rating.rate !== 'number') {
        errors.push('Missing rating.rate');
      } else if (product.rating.rate > 5) {
        errors.push('rating.rate exceeds 5');
      }

      if (errors.length > 0) {
        defectiveProducts.push({
          index,
          id: product.id,
          title: product.title,
          errors
        });
      }
    });

    // 2. Print result
    if (defectiveProducts.length === 0) {
      console.log('✅ No defective products found.');
    } else {
      console.log(`❗ Found ${defectiveProducts.length} defective product(s):\n`);
      defectiveProducts.forEach((item) => {
        console.log(`- [ID ${item.id}] "${item.title}" → ${item.errors.join(', ')}`);
      });
    }

  } catch (error) {
    console.error('❌ API request failed:', error.message);
  }
})();
