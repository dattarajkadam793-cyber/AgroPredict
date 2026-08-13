from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)

# =========================================================
# LOAD MODELS
# =========================================================
crop_model = joblib.load("cropPredModel.pkl")
harvest_model, harvest_scaler = joblib.load("harvestModel.pkl")


# =========================================================
# CROP API (FIXED VERSION)
# =========================================================
@app.route('/predict', methods=['POST'])
def predict():
    print("🔥 CROP API HIT")

    data = request.get_json()
    print("Received:", data)

    if not data:
        return jsonify({"error": "No JSON received"}), 400

    try:
        # ✅ Read inputs (match exact names expected)
        soil_color = data['soilColor']
        N = float(data['Nitrogen'])
        P = float(data['Phosphorus'])
        K = float(data['Potassium'])   # ✅ FIXED CASE
        ph = float(data['pH'])
        rainfall = float(data['Rainfall'])
        temp = float(data['Temperature'])

        # ✅ Create dataframe EXACTLY like training
        df = pd.DataFrame({
            'Nitrogen': [N],
            'Phosphorus': [P],
            'Potassium': [K],
            'pH': [ph],
            'Rainfall': [rainfall],
            'Temperature': [temp],
            'Soil_color': [soil_color]   # ✅ keep raw, encode later
        })

        # ✅ Apply SAME encoding as training
        df = pd.get_dummies(df, columns=['Soil_color'])

        # ✅ Ensure all expected columns exist
        for col in crop_model.feature_names_in_:
            if col not in df:
                df[col] = 0

        # ✅ Ensure correct column order
        df = df[crop_model.feature_names_in_]

        print("Final features:", df)

        # ✅ Predict
        prediction = crop_model.predict(df)[0]

        crop_map = {
            0: "Cotton", 1: "Gram", 2: "Groundnut", 3: "Jowar", 4: "Maize",
            5: "Masoor", 6: "Moong", 7: "Rice", 8: "Sugarcane",
            9: "Tur", 10: "Urad", 11: "Wheat"
        }

        return jsonify({"prediction": crop_map[int(prediction)]})

    except Exception as e:
        print("❌ ERROR:", e)
        return jsonify({"error": str(e)}), 500


# =========================================================
# HARVEST API (ALREADY GOOD, MINOR CLEANUP)
# =========================================================
@app.route('/harvestPredict', methods=['POST'])
def harvest_predict():
    print("🔥 HARVEST API HIT")

    data = request.get_json()
    print("Received:", data)

    try:
        import traceback

        brix = float(data['Brix'])
        pol = float(data['Pol'])
        purity = float(data['Purity'])

        # ✅ Create DataFrame (same as training)
        sample = pd.DataFrame({
            'Brix': [brix],
            'Pol': [pol],
            'Purity': [purity]
        })

        print("Before scaling:", sample)

        # ✅ Use numpy array for scaler (safe)
        sample_scaled = harvest_scaler.transform(sample.values)

        print("After scaling:", sample_scaled)

        pred = harvest_model.predict(sample_scaled)[0]
        score = harvest_model.decision_function(sample_scaled)[0]

        result = "Harvest" if pred == 1 else "Do Not Harvest"

        return jsonify({
            "prediction": result,
            "score": float(score)
        })

    except Exception as e:
        print("❌ ERROR:", e)
        traceback.print_exc()
        return jsonify({"error": str(e)}), 500


# =========================================================
# RUN APP
# =========================================================
if __name__ == "__main__":
    app.run(debug=True)