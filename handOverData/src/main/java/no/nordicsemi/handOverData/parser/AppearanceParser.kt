package no.nordicsemi.handOverData.parser

import no.nordisemi.utils.byteArrayToInt

internal object AppearanceParser {

    /**
     * Parses the appearance characteristics values.
     * The following data type values are assigned in the Bluetooth Specifications [Assigned Number](https://www.bluetooth.com/specifications/assigned-numbers) Bluetooth SIG, June 28, 2023.
     */
    fun parse(appearanceCharacteristics: ByteArray): String {

        return when (byteArrayToInt(appearanceCharacteristics)) {
            0x0000 -> "Generic Unknown"
            0x0040 -> "Generic Phone"
            0x0080 -> "Generic Computer"
            0x0081 -> "Desktop Workstation"
            0x0082 -> "Server - class Computer"
            0x0083 -> "Laptop"
            0x0084 -> "Handheld PC / PDA(clamshell)"
            0x0085 -> "Palm - size PC / PDA"
            0x0086 -> "Wearable computer(watch size)"
            0x0087 -> "Tablet"
            0x0088 -> "Docking Station"
            0x0089 -> "All in One"
            0x008A -> "Blade Server"
            0x008B -> "Convertible"
            0x008C -> "Detachable"
            0x008D -> "IoT Gateway"
            0x008E -> "Mini PC"
            0x008F -> "Stick PC"
            0x00C0 -> "Generic Watch"
            0x00C1 -> "Sports Watch"
            0x00C2 -> "Smartwatch"
            0x0100 -> "Generic Clock"
            0x0140 -> "Generic Display"

            0x0180 -> "Generic Remote Control"
            0x01C0 -> "Generic Eye - glasses"

            0x0200 -> "Generic Tag"
            0x0240 -> "Generic Keyring"

            0x0280 -> "Generic Media Player"
            0x02C0 -> "Generic Barcode Scanner"
            0x0300 -> "Generic Thermometer"

            0x0301 -> "Ear Thermometer"
            0x0340 -> "Generic Heart Rate Sensor"

            0x0341 -> "Heart Rate Belt"
            0x0380 -> "Generic Blood Pressure"
            0x0381 -> "Arm Blood Pressure"
            0x0382 -> "Wrist Blood Pressure"
            0x03C0 -> "Generic Human Interface Device"

            0x03C1 -> "Keyboard"

            0x03C2 -> "Mouse"
            0x03C3 -> "Joystick"
            0x03C4 -> "Gamepad"
            0x03C5 -> "Digitizer Tablet"
            0x03C6 -> "Card Reader"
            0x03C7 -> "Digital Pen"

            0x03C8 -> "Barcode Scanner"
            0x03C9 -> "Touchpad"

            0x03CA -> "Presentation Remote"
            0x0400 -> "Generic Glucose Meter"

            0x0440 -> "Generic Running Walking Sensor"
            0x0441 -> "In - Shoe Running Walking Sensor"
            0x0442 -> "On -Shoe Running Walking Sensor"
            0x0443 -> "On -Hip Running Walking Sensor"
            0x0480 -> "Generic Cycling"

            0x0481 -> "Cycling Computer"
            0x0482 -> "Speed Sensor"
            0x0483 -> "Cadence Sensor"

            0x0484 -> "Power Sensor"
            0x0485 -> "Speed and Cadence Sensor"
            0x04C0 -> "Generic Control Device"
            0x04C1 -> "Switch"
            0x04C2 -> "Multi -switch"

            0x04C3 -> "Button"
            0x04C4 -> "Slider"
            0x04C5 -> "Rotary Switch"
            0x04C6 -> "Touch Panel"
            0x04C7 -> "Single Switch"

            0x04C8 -> "Double Switch"
            0x04C9 -> "Triple Switch"
            0x04CA -> "Battery Switch"

            0x04CB -> "Energy Harvesting Switch"
            0x04CC -> "Push Button"
            0x0500 -> "Generic Network Device"

            0x0501 -> "Access Point"
            0x0502 -> "Mesh Device"
            0x0503 -> "Mesh Network Proxy"
            0x0540 -> "Generic Sensor"

            0x0541 -> "Motion Sensor"
            0x0542 -> "Air quality Sensor"

            0x0543 -> "Temperature Sensor"
            0x0544 -> "Humidity Sensor"
            0x0545 -> "Leak Sensor"

            0x0546 -> "Smoke Sensor"

            0x0547 -> "Occupancy Sensor"
            0x0548 -> "Contact Sensor"

            0x0549 -> "Carbon Monoxide Sensor"
            0x054A -> "Carbon Dioxide Sensor"
            0x054B -> "Ambient Light Sensor"
            0x054C -> "Energy Sensor"

            0x054D -> "Color Light Sensor"

            0x054E -> "Rain Sensor"
            0x054F -> "Fire Sensor"
            0x0550 -> "Wind Sensor"

            0x0551 -> "Proximity Sensor"
            0x0552 -> "Multi - Sensor"

            0x0553 -> "Flush Mounted Sensor"
            0x0554 -> "Ceiling Mounted Sensor"
            0x0555 -> "Wall Mounted Sensor"
            0x0556 -> "Multisensor"
            0x0557 -> "Energy Meter"
            0x0558 -> "Flame Detector"
            0x0559 -> "Vehicle Tire Pressure Sensor"

            0x0580 -> "Generic Light Fixtures"
            0x0581 -> "Wall Light"
            0x0582 -> "Ceiling Light"
            0x0583 -> "Floor Light"

            0x0584 -> "Cabinet Light"
            0x0585 -> "Desk Light"
            0x0586 -> "Troffer Light"

            0x0587 -> "Pendant Light"
            0x0588 -> "In - ground Light"
            0x0589 -> "Flood Light"

            0x058A -> "Underwater Light"
            0x058B -> "Bollard with Light"

            0x058C -> "Pathway Light"
            0x058D -> "Garden Light"
            0x058E -> "Pole -top Light"
            0x058F -> "Spotlight"
            0x0590 -> "Linear Light"

            0x0591 -> "Street Light"
            0x0592 -> "Shelves Light"
            0x0593 -> "Bay Light"

            0x0594 -> "Emergency Exit Light"
            0x0595 -> "Light Controller"
            0x0596 -> "Light Driver"
            0x0597 -> "Bulb"
            0x0598 -> "Low -bay Light"
            0x0599 -> "High -bay Light"
            0x05C0 -> "Generic Fan"

            0x05C1 -> "Ceiling Fan"
            0x05C2 -> "Axial Fan"
            0x05C3 -> "Exhaust Fan"

            0x05C4 -> "Pedestal Fan"
            0x05C5 -> "Desk Fan"
            0x05C6 -> "Wall Fan"

            0x0600 -> "Generic HVAC"
            0x0601 -> "Thermostat"

            0x0602 -> "Humidifier"
            0x0603 -> "De -humidifier"
            0x0604 -> "Heater"
            0x0605 -> "Radiator"
            0x0606 -> "Boiler"
            0x0607 -> "Heat Pump"
            0x0608 -> "Infrared Heater"
            0x0609 -> "Radiant Panel Heater"
            0x060A -> "Fan Heater"

            0x060B -> "Air Curtain"
            0x0640 -> "Generic Air Conditioning"

            0x0680 -> "Generic Humidifier"
            0x06C0 -> "Generic Heating"
            0x06C1 -> "Radiator"
            0x06C2 -> "Boiler"
            0x06C3 -> "Heat Pump"

            0x06C4 -> "Infrared Heater"
            0x06C5 -> "Radiant Panel Heater"

            0x06C6 -> "Fan Heater"
            0x06C7 -> "Air Curtain"
            0x0700 -> "Generic Access Control"
            0x0701 -> "Access Door"

            0x0702 -> "Garage Door"
            0x0703 -> "Emergency Exit Door"

            0x0704 -> "Access Lock"
            0x0705 -> "Elevator"

            0x0706 -> "Window"
            0x0707 -> "Entrance Gate"
            0x0708 -> "Door Lock"
            0x0709 -> "Locker"
            0x0740 -> "Generic Motorized Device"
            0x0741 -> "Motorized Gate"

            0x0742 -> "Awning"
            0x0743 -> "Blinds or Shades"
            0x0744 -> "Curtains"
            0x0745 -> "Screen"
            0x0780 -> "Generic Power Device"
            0x0781 -> "Power Outlet"
            0x0782 -> "Power Strip"
            0x0783 -> "Plug"
            0x0784 -> "Power Supply"

            0x0785 -> "LED Driver"
            0x0786 -> "Fluorescent Lamp Gear"

            0x0787 -> "HID Lamp Gear"
            0x0788 -> "Charge Case"
            0x0789 -> "Power Bank"
            0x07C0 -> "Generic Light Source"
            0x07C1 -> "Incandescent Light Bulb"
            0x07C2 -> "LED Lamp"

            0x07C3 -> "HID Lamp"
            0x07C4 -> "Fluorescent Lamp"
            0x07C5 -> "LED Array"

            0x07C6 -> "Multi -Color LED Array"
            0x07C7 -> "Low voltage halogen"

            0x07C8 -> "Organic light emitting diode(OLED)"
            0x0800 -> "Generic Window Covering"
            0x0801 -> "Window Shades"

            0x0802 -> "Window Blinds"
            0x0803 -> "Window Awning"
            0x0804 -> "Window Curtain"

            0x0805 -> "Exterior Shutter"
            0x0806 -> "Exterior Screen"
            0x0840 -> "Generic Audio Sink"
            0x0841 -> "Standalone Speaker"

            0x0842 -> "Soundbar"
            0x0843 -> "Bookshelf Speaker"
            0x0844 -> "Standmounted Speaker"
            0x0845 -> "Speakerphone"
            0x0880 -> "Generic Audio Source"
            0x0881 -> "Microphone"
            0x0882 -> "Alarm"
            0x0883 -> "Bell"
            0x0884 -> "Horn"
            0x0885 -> "Broadcasting Device"
            0x0886 -> "Service Desk"
            0x0887 -> "Kiosk"
            0x0888 -> "Broadcasting Room"

            0x0889 -> "Auditorium"
            0x08C0 -> "Generic Motorized Vehicle"
            0x08C1 -> "Car"
            0x08C2 -> "Large Goods Vehicle"
            0x08C3 -> "2-Wheeled Vehicle"
            0x08C4 -> "Motorbike"
            0x08C5 -> "Scooter"
            0x08C6 -> "Moped"
            0x08C7 -> "3 - Wheeled Vehicle"
            0x08C8 -> "Light Vehicle"
            0x08C9 -> "Quad Bike"
            0x08CA -> "Minibus"
            0x08CB -> "Bus"
            0x08CC -> "Trolley"
            0x08CD -> "Agricultural Vehicle"

            0x08CE -> "Camper / Caravan"
            0x08CF -> "Recreational Vehicle / Motor Home"
            0x0900 -> "Generic Domestic Appliance"
            0x0901 -> "Refrigerator"
            0x0902 -> "Freezer"
            0x0903 -> "Oven"
            0x0904 -> "Microwave"
            0x0905 -> "Toaster"
            0x0906 -> "Washing Machine"
            0x0907 -> "Dryer"

            0x0908 -> "Coffee maker"
            0x0909 -> "Clothes iron"
            0x090A -> "Curling iron"

            0x090B -> "Hair dryer"
            0x090C -> "Vacuum cleaner"
            0x090D -> "Robotic vacuum cleaner"
            0x090E -> "Rice cooker"

            0x090F -> "Clothes steamer"
            0x0940 -> "Generic Wearable Audio Device"
            0x0941 -> "Earbud"
            0x0942 -> "Headset"
            0x0943 -> "Headphones"
            0x0944 -> "Neck Band"

            0x0980 -> "Generic Aircraft"
            0x0981 -> "Light Aircraft"
            0x0982 -> "Microlight"
            0x0983 -> "Paraglider"
            0x0984 -> "Large Passenger Aircraft"
            0x09C0 -> "Generic AV Equipment"
            0x09C1 -> "Amplifier"
            0x09C2 -> "Receiver"
            0x09C3 -> "Radio"
            0x09C4 -> "Tuner"
            0x09C5 -> "Turntable"
            0x09C6 -> "CD Player"

            0x09C7 -> "DVD Player"
            0x09C8 -> "Bluray Player"

            0x09C9 -> "Optical Disc Player"
            0x09CA -> "Set -Top Box"
            0x0A00 -> "Generic Display Equipment"
            0x0A01 -> "Television"
            0x0A02 -> "Monitor"
            0x0A03 -> "Projector"
            0x0A40 -> "Generic Hearing aid"
            0x0A41 -> "In -ear hearing aid"

            0x0A42 -> "Behind - ear hearing aid"

            0x0A43 -> "Cochlear Implant"
            0x0A80 -> "Generic Gaming"

            0x0A81 -> "Home Video Game Console"

            0x0A82 -> "Portable handheld console"

            0x0AC0 -> "Generic Signage"
            0x0AC1 -> "Digital Signage"
            0x0AC2 -> "Electronic Label"

            0x0C40 -> "Generic Pulse Oximeter"
            0x0C41 -> "Fingertip Pulse Oximeter"
            0x0C42 -> "Wrist Worn Pulse Oximeter"
            0x0C80 -> "Generic Weight Scale"

            0x0CC0 -> "Generic Personal Mobility Device"
            0x0CC1 -> "Powered Wheelchair"
            0x0CC2 -> "Mobility Scooter"

            0x0D00 -> "Generic Continuous Glucose Monitor"
            0x0D40 -> "Generic Insulin Pump"

            0x0D41 -> "Insulin Pump, durable pump"
            0x0D44 -> "Insulin Pump, patch pump"
            0x0D48 -> "Insulin Pen"
            0x0D80 -> "Generic Medication Delivery"

            0x0DC0 -> "Generic Spirometer"
            0x0DC1 -> "Handheld Spirometer"
            0x1440 -> "Generic Outdoor Sports Activity"

            0x1441 -> "Location Display"
            0x1442 -> "Location and Navigation Display"
            0x1443 -> "Location Pod"

            0x1444 -> "Location and Navigation Pod"
            else -> "INVALID DATA"
        }
    }
}