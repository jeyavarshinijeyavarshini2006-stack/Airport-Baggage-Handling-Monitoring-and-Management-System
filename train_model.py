import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score

# Load dataset
df = pd.read_csv("baggage_dataset.csv")

# Convert text values into numbers
le_flight = LabelEncoder()
le_risk = LabelEncoder()
le_status = LabelEncoder()

df["flight_type"] = le_flight.fit_transform(df["flight_type"])
df["risk_level"] = le_risk.fit_transform(df["risk_level"])
df["status"] = le_status.fit_transform(df["status"])

# Input features
X = df[[
    "flight_type",
    "processing_time",
    "risk_level",
    "status"
]]

# Target
y = df["delayed"].map({"No": 0, "Yes": 1})

# Split dataset
X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42,
    stratify=y
)

# Create ML model
model = DecisionTreeClassifier(
    random_state=42,
    max_depth=4
)

# Train model
model.fit(X_train, y_train)

# Test model
y_pred = model.predict(X_test)

# Accuracy
accuracy = accuracy_score(y_test, y_pred)

print("====================================")
print(" BAGGAGE DELAY PREDICTION MODEL")
print("====================================")
print("Dataset Size :", len(df))
print("Training Data:", len(X_train))
print("Testing Data :", len(X_test))
print("Accuracy     :", round(accuracy * 100, 2), "%")
print("====================================")
import joblib

joblib.dump(model, "baggage_delay_model.pkl")

print("Model saved successfully!")