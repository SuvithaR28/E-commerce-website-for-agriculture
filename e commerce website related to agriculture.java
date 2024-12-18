from flask import Flask, render_template, request, redirect, url_for

app = Flask(__name__)

# Sample list of products related to agriculture
products = [
    {'id': 1, 'name': 'Organic Fertilizer', 'price': 10.0, 'description': 'A natural fertilizer for your crops.'},
    {'id': 2, 'name': 'Seeds for Wheat', 'price': 5.0, 'description': 'High-quality wheat seeds.'},
    {'id': 3, 'name': 'Water Irrigation System', 'price': 50.0, 'description': 'Automatic irrigation for your farm.'},
]

cart = []

# Route to view the products
@app.route('/')
def index():
    return render_template('index.html', products=products)

# Route to add a product to the cart
@app.route('/add_to_cart/<int:product_id>')
def add_to_cart(product_id):
    product = next((item for item in products if item['id'] == product_id), None)
    if product:
        cart.append(product)
    return redirect(url_for('cart'))

# Route to view the cart
@app.route('/cart')
def view_cart():
    return render_template('cart.html', cart=cart)

# Route to checkout
@app.route('/checkout')
def checkout():
    total = sum(item['price'] for item in cart)
    return render_template('checkout.html', cart=cart, total=total)

if __name__ == '__main__':
    app.run(debug=True)

//CREATING HTML TEMPLATE
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agriculture E-Commerce</title>
</head>
<body>
    <h1>Welcome to our Agriculture Store</h1>
    <h2>Products</h2>
    <ul>
        {% for product in products %}
        <li>
            <h3>{{ product.name }}</h3>
            <p>{{ product.description }}</p>
            <p>Price: ${{ product.price }}</p>
            <a href="{{ url_for('add_to_cart', product_id=product.id) }}">Add to Cart</a>
        </li>
        {% endfor %}
    </ul>
    <a href="{{ url_for('cart') }}">Go to Cart</a>
</body>
</html>

//CART PAGE

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart</title>
</head>
<body>
    <h1>Your Cart</h1>
    <ul>
        {% for item in cart %}
        <li>
            <h3>{{ item.name }}</h3>
            <p>Price: ${{ item.price }}</p>
        </li>
        {% endfor %}
    </ul>
    <h2>Total: ${{ cart|sum(attribute='price') }}</h2>
    <a href="{{ url_for('checkout') }}">Checkout</a>
    <br>
    <a href="{{ url_for('index') }}">Continue Shopping</a>
</body>
</html>


//CHEKOUT PAGE

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout</title>
</head>
<body>
    <h1>Checkout</h1>
    <ul>
        {% for item in cart %}
        <li>
            <h3>{{ item.name }}</h3>
            <p>Price: ${{ item.price }}</p>
        </li>
        {% endfor %}
    </ul>
    <h2>Total: ${{ total }}</h2>
    <p>Thank you for shopping with us!</p>
</body>
</html>




