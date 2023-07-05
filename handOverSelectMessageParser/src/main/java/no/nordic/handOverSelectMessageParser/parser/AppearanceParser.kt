package no.nordic.handOverSelectMessageParser.parser

import no.nordic.handOverSelectMessageParser.utility.toHex

internal object AppearanceParser {

    /**
     * Parses the appearance characteristics values.
     * The following data type values are assigned in the Bluetooth Specifications [Assigned Number](https://www.bluetooth.com/specifications/assigned-numbers) Bluetooth SIG, June 28, 2023.
     */
    fun parse(appearanceCharacteristics: ByteArray): String {
        return when (appearanceCharacteristics.toHex()) {
            "0000" -> "Generic Unknown"
            "0040" -> "Generic Phone"
            "0080" -> "Generic Computer"
            "0081" -> "Desktop Workstation"
            "0082" -> "Server - class Computer"
            "0083" -> "Laptop"
            "0084" -> "Handheld PC / PDA(clamshell)"
            "0085" -> "Palm - size PC / PDA"
            "0086" -> "Wearable computer(watch size)"
            "0087" -> "Tablet"
            "0088" -> "Docking Station"
            "0089" -> "All in One"
            "008A" -> "Blade Server"
            "008B" -> "Convertible"
            "008C" -> "Detachable"
            "008D" -> "IoT Gateway"
            "008E" -> "Mini PC"
            "008F" -> "Stick PC"
            "00C0" -> "Generic Watch"
            "00C1" -> "Sports Watch"
            "00C2" -> "Smartwatch"
            "0100" -> "Generic Clock"
            "0140" -> "Generic Display"

            "0180" -> "Generic Remote Control"
            "01C0" -> "Generic Eye - glasses"

            "0200" -> "Generic Tag"
            "0240" -> "Generic Keyring"

            "0280" -> "Generic Media Player"
            "02C0" -> "Generic Barcode Scanner"
            "0300" -> "Generic Thermometer"

            "0301" -> "Ear Thermometer"
            "0340" -> "Generic Heart Rate Sensor"

            "0341" -> "Heart Rate Belt"
            "0380" -> "Generic Blood Pressure"
            "0381" -> "Arm Blood Pressure"
            "0382" -> "Wrist Blood Pressure"
            "03C0" -> "Generic Human Interface Device"

            "03C1" -> "Keyboard"

            "03C2" -> "Mouse"
            "03C3" -> "Joystick"
            "03C4" -> "Gamepad"
            "03C5" -> "Digitizer Tablet"
            "03C6" -> "Card Reader"
            "03C7" -> "Digital Pen"

            "03C8" -> "Barcode Scanner"
            "03C9" -> "Touchpad"

            "03CA" -> "Presentation Remote"
            "0400" -> "Generic Glucose Meter"

            "0440" -> "Generic Running Walking Sensor"
            "0441" -> "In - Shoe Running Walking Sensor"
            "0442" -> "On -Shoe Running Walking Sensor"
            "0443" -> "On -Hip Running Walking Sensor"
            "0480" -> "Generic Cycling"

            "0481" -> "Cycling Computer"
            "0482" -> "Speed Sensor"
            "0483" -> "Cadence Sensor"

            "0484" -> "Power Sensor"
            "0485" -> "Speed and Cadence Sensor"
            "04C0" -> "Generic Control Device"
            "04C1" -> "Switch"
            "04C2" -> "Multi -switch"

            "04C3" -> "Button"
            "04C4" -> "Slider"
            "04C5" -> "Rotary Switch"
            "04C6" -> "Touch Panel"
            "04C7" -> "Single Switch"

            "04C8" -> "Double Switch"
            "04C9" -> "Triple Switch"
            "04CA" -> "Battery Switch"

            "04CB" -> "Energy Harvesting Switch"
            "04CC" -> "Push Button"
            "0500" -> "Generic Network Device"

            "0501" -> "Access Point"
            "0502" -> "Mesh Device"
            "0503" -> "Mesh Network Proxy"
            "0540" -> "Generic Sensor"

            "0541" -> "Motion Sensor"
            "0542" -> "Air quality Sensor"

            "0543" -> "Temperature Sensor"
            "0544" -> "Humidity Sensor"
            "0545" -> "Leak Sensor"

            "0546" -> "Smoke Sensor"

            "0547" -> "Occupancy Sensor"
            "0548" -> "Contact Sensor"

            "0549" -> "Carbon Monoxide Sensor"
            "054A" -> "Carbon Dioxide Sensor"
            "054B" -> "Ambient Light Sensor"
            "054C" -> "Energy Sensor"

            "054D" -> "Color Light Sensor"

            "054E" -> "Rain Sensor"
            "054F" -> "Fire Sensor"
            "0550" -> "Wind Sensor"

            "0551" -> "Proximity Sensor"
            "0552" -> "Multi - Sensor"

            "0553" -> "Flush Mounted Sensor"
            "0554" -> "Ceiling Mounted Sensor"
            "0555" -> "Wall Mounted Sensor"
            "0556" -> "Multisensor"
            "0557" -> "Energy Meter"
            "0558" -> "Flame Detector"
            "0559" -> "Vehicle Tire Pressure Sensor"

            "0580" -> "Generic Light Fixtures"
            "0581" -> "Wall Light"
            "0582" -> "Ceiling Light"
            "0583" -> "Floor Light"

            "0584" -> "Cabinet Light"
            "0585" -> "Desk Light"
            "0586" -> "Troffer Light"

            "0587" -> "Pendant Light"
            "0588" -> "In - ground Light"
            "0589" -> "Flood Light"

            "058A" -> "Underwater Light"
            "058B" -> "Bollard with Light"

            "058C" -> "Pathway Light"
            "058D" -> "Garden Light"
            "058E" -> "Pole -top Light"
            "058F" -> "Spotlight"
            "0590" -> "Linear Light"

            "0591" -> "Street Light"
            "0592" -> "Shelves Light"
            "0593" -> "Bay Light"

            "0594" -> "Emergency Exit Light"
            "0595" -> "Light Controller"
            "0596" -> "Light Driver"
            "0597" -> "Bulb"
            "0598" -> "Low -bay Light"
            "0599" -> "High -bay Light"
            "05C0" -> "Generic Fan"

            "05C1" -> "Ceiling Fan"
            "05C2" -> "Axial Fan"
            "05C3" -> "Exhaust Fan"

            "05C4" -> "Pedestal Fan"
            "05C5" -> "Desk Fan"
            "05C6" -> "Wall Fan"

            "0600" -> "Generic HVAC"
            "0601" -> "Thermostat"

            "0602" -> "Humidifier"
            "0603" -> "De -humidifier"
            "0604" -> "Heater"
            "0605" -> "Radiator"
            "0606" -> "Boiler"
            "0607" -> "Heat Pump"
            "0608" -> "Infrared Heater"
            "0609" -> "Radiant Panel Heater"
            "060A" -> "Fan Heater"

            "060B" -> "Air Curtain"
            "0640" -> "Generic Air Conditioning"

            "0680" -> "Generic Humidifier"
            "06C0" -> "Generic Heating"
            "06C1" -> "Radiator"
            "06C2" -> "Boiler"
            "06C3" -> "Heat Pump"

            "06C4" -> "Infrared Heater"
            "06C5" -> "Radiant Panel Heater"

            "06C6" -> "Fan Heater"
            "06C7" -> "Air Curtain"
            "0700" -> "Generic Access Control"
            "0701" -> "Access Door"

            "0702" -> "Garage Door"
            "0703" -> "Emergency Exit Door"

            "0704" -> "Access Lock"
            "0705" -> "Elevator"

            "0706" -> "Window"
            "0707" -> "Entrance Gate"
            "0708" -> "Door Lock"
            "0709" -> "Locker"
            "0740" -> "Generic Motorized Device"
            "0741" -> "Motorized Gate"

            "0742" -> "Awning"
            "0743" -> "Blinds or Shades"
            "0744" -> "Curtains"
            "0745" -> "Screen"
            "0780" -> "Generic Power Device"
            "0781" -> "Power Outlet"
            "0782" -> "Power Strip"
            "0783" -> "Plug"
            "0784" -> "Power Supply"

            "0785" -> "LED Driver"
            "0786" -> "Fluorescent Lamp Gear"

            "0787" -> "HID Lamp Gear"
            "0788" -> "Charge Case"
            "0789" -> "Power Bank"
            "07C0" -> "Generic Light Source"
            "07C1" -> "Incandescent Light Bulb"
            "07C2" -> "LED Lamp"

            "07C3" -> "HID Lamp"
            "07C4" -> "Fluorescent Lamp"
            "07C5" -> "LED Array"

            "07C6" -> "Multi -Color LED Array"
            "07C7" -> "Low voltage halogen"

            "07C8" -> "Organic light emitting diode(OLED)"
            "0800" -> "Generic Window Covering"
            "0801" -> "Window Shades"

            "0802" -> "Window Blinds"
            "0803" -> "Window Awning"
            "0804" -> "Window Curtain"

            "0805" -> "Exterior Shutter"
            "0806" -> "Exterior Screen"
            "0840" -> "Generic Audio Sink"
            "0841" -> "Standalone Speaker"

            "0842" -> "Soundbar"
            "0843" -> "Bookshelf Speaker"
            "0844" -> "Standmounted Speaker"
            "0845" -> "Speakerphone"
            "0880" -> "Generic Audio Source"
            "0881" -> "Microphone"
            "0882" -> "Alarm"
            "0883" -> "Bell"
            "0884" -> "Horn"
            "0885" -> "Broadcasting Device"
            "0886" -> "Service Desk"
            "0887" -> "Kiosk"
            "0888" -> "Broadcasting Room"

            "0889" -> "Auditorium"
            "08C0" -> "Generic Motorized Vehicle"
            "08C1" -> "Car"
            "08C2" -> "Large Goods Vehicle"
            "08C3" -> "2-Wheeled Vehicle"
            "08C4" -> "Motorbike"
            "08C5" -> "Scooter"
            "08C6" -> "Moped"
            "08C7" -> "3 - Wheeled Vehicle"
            "08C8" -> "Light Vehicle"
            "08C9" -> "Quad Bike"
            "08CA" -> "Minibus"
            "08CB" -> "Bus"
            "08CC" -> "Trolley"
            "08CD" -> "Agricultural Vehicle"

            "08CE" -> "Camper / Caravan"
            "08CF" -> "Recreational Vehicle / Motor Home"
            "0900" -> "Generic Domestic Appliance"
            "0901" -> "Refrigerator"
            "0902" -> "Freezer"
            "0903" -> "Oven"
            "0904" -> "Microwave"
            "0905" -> "Toaster"
            "0906" -> "Washing Machine"
            "0907" -> "Dryer"

            "0908" -> "Coffee maker"
            "0909" -> "Clothes iron"
            "090A" -> "Curling iron"

            "090B" -> "Hair dryer"
            "090C" -> "Vacuum cleaner"
            "090D" -> "Robotic vacuum cleaner"
            "090E" -> "Rice cooker"

            "090F" -> "Clothes steamer"
            "0940" -> "Generic Wearable Audio Device"
            "0941" -> "Earbud"
            "0942" -> "Headset"
            "0943" -> "Headphones"
            "0944" -> "Neck Band"

            "0980" -> "Generic Aircraft"
            "0981" -> "Light Aircraft"
            "0982" -> "Microlight"
            "0983" -> "Paraglider"
            "0984" -> "Large Passenger Aircraft"
            "09C0" -> "Generic AV Equipment"
            "09C1" -> "Amplifier"
            "09C2" -> "Receiver"
            "09C3" -> "Radio"
            "09C4" -> "Tuner"
            "09C5" -> "Turntable"
            "09C6" -> "CD Player"

            "09C7" -> "DVD Player"
            "09C8" -> "Bluray Player"

            "09C9" -> "Optical Disc Player"
            "09CA" -> "Set -Top Box"
            "0A00" -> "Generic Display Equipment"
            "0A01" -> "Television"
            "0A02" -> "Monitor"
            "0A03" -> "Projector"
            "0A40" -> "Generic Hearing aid"
            "0A41" -> "In -ear hearing aid"

            "0A42" -> "Behind - ear hearing aid"

            "0A43" -> "Cochlear Implant"
            "0A80" -> "Generic Gaming"

            "0A81" -> "Home Video Game Console"

            "0A82" -> "Portable handheld console"

            "0AC0" -> "Generic Signage"
            "0AC1" -> "Digital Signage"
            "0AC2" -> "Electronic Label"

            "0C40" -> "Generic Pulse Oximeter"
            "0C41" -> "Fingertip Pulse Oximeter"
            "0C42" -> "Wrist Worn Pulse Oximeter"
            "0C80" -> "Generic Weight Scale"

            "0CC0" -> "Generic Personal Mobility Device"
            "0CC1" -> "Powered Wheelchair"
            "0CC2" -> "Mobility Scooter"

            "0D00" -> "Generic Continuous Glucose Monitor"
            "0D40" -> "Generic Insulin Pump"

            "0D41" -> "Insulin Pump, durable pump"
            "0D44" -> "Insulin Pump, patch pump"
            "0D48" -> "Insulin Pen"
            "0D80" -> "Generic Medication Delivery"

            "0DC0" -> "Generic Spirometer"
            "0DC1" -> "Handheld Spirometer"
            "1440" -> "Generic Outdoor Sports Activity"

            "1441" -> "Location Display"
            "1442" -> "Location and Navigation Display"
            "1443" -> "Location Pod"

            "1444" -> "Location and Navigation Pod"
            else -> "INVALID DATA"
        }
    }
}