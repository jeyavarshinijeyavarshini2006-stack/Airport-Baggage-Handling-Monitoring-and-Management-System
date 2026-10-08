import sys
import pandas as pd
import joblib

# Load trained ML model
model = joblib.load("baggage_delay_model.pkl")

# Get values from Java
flight_type = sys.argv[1]
processing_time = int(sys.argv[2])
risk_level = sys.argv[3]
status = sys.argv[4]

# Same encoding used during model training
flight_map = {
    "Domestic": 0,
    "International": 1
}

risk_map = {
    "High": 0,
    "Low": 1,
    "Medium": 2
}

status_map = {
    "Baggage Claim": 0,
    "Checked-In": 1,
    "Delayed": 2,
    "In Transit": 3,
    "Loading": 4,
    "Lost": 5,
    "Misrouted": 6,
    "Security Check": 7,
    "Security Hold": 8,
    "Sorting": 9,
    "Unloading": 10
}

# Create input data
input_data = pd.DataFrame([{
    "flight_type": flight_map[flight_type],
    "processing_time": processing_time,
    "risk_level": risk_map[risk_level],
    "status": status_map[status]
}])

# Make prediction
prediction = model.predict(input_data)[0]

# Display prediction
if prediction == 1:
    print("DELAYED")
else:
    print("ON TIME")