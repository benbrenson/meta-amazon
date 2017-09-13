DESCRIPTION = "Amazon cloud services SDK for IoT based applications in embedded C."
LICENSE = "gpl2"

inherit dpkg debianize


URL="git://github.com/aws/aws-iot-device-sdk-embedded-C.git"
BRANCH="master"
SRCREV = "70071112bd5e1c5b9f150894fafe199637b4f63a"

SRC_DIR = "git"
SRC_URI += "${URL};branch=${BRANCH};tag=${TAG};protocol=https \
            file://debian \
           "

SECTION = "utils"
PRIORITY = "optional"