DESCRIPTION = "Amazon cloud services SDK for IoT based applications in C++."
LICENSE = "gpl2"

inherit dpkg debianize


URL="git://github.com/aws/aws-iot-device-sdk-cpp.git"
BRANCH="master"
SRCREV = "63e8ce25e6b282719d112f3f58966cc1e78d271b"

SRC_DIR = "git"
SRC_URI += "${URL};branch=${BRANCH};protocol=https \
            file://debian \
           "

SECTION = "utils"
PRIORITY = "optional"


