# NFC Connect

The repository is no longer being managed. To view the latest recent code, click [NFC-Connect](https://github.com/NordicSemiconductor/NFC-Connect) link.

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
    * Tag

      The NFC tag is a passive device. The tag contains data, such as a URL, which can be read and
      written by the polling device. Most of Nordic Semiconductor’s nRF5 Series devices contain a
      peripheral called NFC tag. This peripheral works in NFC-A technology and supports the “listen”
      communication mode. Listen mode means that the tag “listens to” (or waits for) polling
      devices, but it does not actively start a connection.
    * Tag Types

      The NFC forum defines four different tag types as type 1, type 2, type 3 and type 4. Theses
      tag types differ in their memory size and other features.
    * NDEF

      NFC communication uses NFC Data Exchange Format (NDEF) messages to exchange data. NDEF is a
      binary format that is commonly used in NFC devices (like smartphones) and NFC tags.
    * NDEF message

      The main data container defined by NDEF is called an NDEF message. An NDEF message
      contains one or more NDEF records or different types. The type indicates the kind of data
      that the record contains, and the series of record types in a message defines the message
      type. For example, a URI message contains one record that encodes a URL string.
    * Ndef Record

      NDEF Records contain a specific payload, and have the following structure that identifies
      the contents and size of the record. The Record header contains metadata about, amongst
      others, the payload type and length. The Record payload constitutes the actual content of
      the record. See more
      on [NDEF message and record format](https://developer.nordicsemi.com/nRF_Connect_SDK/doc/latest/nrf/protocols/nfc/index.html#ndef-message-and-record-format ).
      The Ndef record header consists of 1
      bytes [Flags and TNF](https://developer.nordicsemi.com/nRF_Connect_SDK/doc/latest/nrf/protocols/nfc/index.html#record-header)
      which contains following flags:
        * MB (Message Begin) and ME (Message End):

          Specify the position of the NDEF record within the message. The MB flag is set for the
          first record in the message. Similarly, the ME flag is set for the last record in the
          message. If a record is the only record in a message, both flags are set.
        * CF (Chunk Flag):

          Used for chunked payloads (a payload that is partitioned into multiple records). Set
          in all chunks of the record except for the last one. Note, however, that chunking is
          not supported by this library.
        * SR (Short Record):

          Used to determine the size of the payload length field. If the flag is set, the
          Payload Length occupies 1 byte; otherwise it is 4 bytes long. Note that the NDEF
          generator supports a Payload Length of 4 bytes only at the moment.
        * IL (ID Length present):

          Indicates whether an ID Length field is present in the header. If the flag is set, the
          ID Length field is present.

        * TNF (Type Name Format):

          Specifies the structure of the Payload Type field and how to interpret it. The
          following values are allowed (square brackets contain documentation reference related
          to the specific type):

          | Value 	 | Type Name Format                    	            |
                                    |---------|---------------------------------------------------|
          | 0x00  	 | Empty                               	            |
          | 0x01  	 | NFC Forum well-known type [NFC RTD] 	            |
          | 0x02  	 | Media-type [RFC 2046]               	            |
          | 0x03  	 | Absolute URI [RFC 3986]               	        |
          | 0x04  	 | NFC Forum external type [NFC RTD]               	|
          | 0x05  	 | Unknown               	                        |
          | 0x06  	 | Unchanged               	                        |
          | 0x07  	 | Reserved              	                        |

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
