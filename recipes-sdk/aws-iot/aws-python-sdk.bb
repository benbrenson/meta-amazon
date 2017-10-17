DESCRIPTION = "Amazon cloud services SDK for IoT based applications in python."
LICENSE = "gpl2"

inherit dpkg debianize-python

DEP_RDEPENDS = "openssl"

PYTHON_VERSION = "3"

URL="git://github.com/aws/aws-iot-device-sdk-python.git"
BRANCH="master"
SRCREV = "333ffdb00588a7bcd80b21d352d6f4f44597c9a5"

SRC_DIR = "git"
SRC_URI += "${URL};branch=${BRANCH};protocol=https \
            file://debian \
           "

SECTION = "utils"
PRIORITY = "optional"