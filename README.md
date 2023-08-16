# NFC Connect

This is a tool to read the NFC tag and its details. If the NFC record contains the Bluetooth oob
data, then it handovers the connection to Bluetooth from NFC.

<details>
<summary>About NFC </summary>
Near Field Communication (NFC) is a technology for wireless transfer of small amounts of data between two devices. NFC communication happens when two devices are brought together, within a 10 cm distance between them. A simple touch or waving can establish the connection between the devices. Initiation of NFC service needs two devices, i.e. a host device and a counterpart device to work with.

NFC Forum is a non-profit organization which aims at advancing the applications of NFC in mobile
devices, consumer electronics and personal computing devices. It develops specifications which
ensure security and interoperability for the NFC supported devices.

1. Different terms to be used in this file:
    * NFC Reader Device / Polling Device

      To read data from an NFC tag, a polling device (also called NFC Reader Device) is needed. In
      most cases, this will be a mobile device with an NFC interface, such as a smartphone or a
      tablet.
      tablet.
    * Tag

      The NFC tag is passive device. The tag contains data, such as a URL, which can be read and
      written by the polling device. Most of Nordic Semiconductor’s nRF5 Series devices contain a
      peripheral called NFC tag. This peripheral works in NFC-A technology and supports the “listen”
      communication mode. Listen mode means that the tag “listens to” (or waits for) polling
      devices, but it does not actively start a connection.

    * NDEF

      NFC communication uses NFC Data Exchange Format (NDEF) messages to exchange data. NDEF is a
      binary format that is commonly used in NFC devices (like smartphones) and NFC tags.
    * NDEF message

      The main data container defined by NDEF is called an NDEF message. An NDEF message contains
      one or more NDEF records or different types. The type indicates the kind of data that the
      record contains, and the series of record types in a message defines the message type. For
      example, a URI message contains one record that encodes a URL string.

* Tag Types

  The NFC forum defines four different tag types as type 1, type 2, type 3 and type 4. Theses
  tag types differ in their memory size and other features.


* Ndef Record
*

2.

</details>

<details>
<summary> About Nordic NFC </summary>
</details>

<details>
<summary> About App </summary>
</details>

## About App

## About Nordic NFC

## Features

## System Architecture

## How to use the app

## System requirement
