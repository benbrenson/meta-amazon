DESCRIPTION = "Amazon cloud services SDK for IoT based applications in embedded C."
LICENSE = "gpl2"

inherit dpkg debianize

DEB_DEPENDS += "libmbedtls-dev"

URL="git://github.com/aws/aws-iot-device-sdk-embedded-C.git"
BRANCH="master"
SRCREV = "70071112bd5e1c5b9f150894fafe199637b4f63a"

SRC_DIR = "git"
SRC_URI += "${URL};branch=${BRANCH};protocol=https \
            file://debian \
           "

SECTION = "utils"
PRIORITY = "optional"


# Note: The github repository contains a toplevel makefile which should not be used.
# This makefile will build cpputest as source code test engine and this is not
# the way in we will make use of this repository.
# This repository instead contains the complete aws iot sdk and a potential software will
# make of use it later. In order to achive that, this repository has to be ported for
# specific applications, which is done by defining the required locations of this sdk within
# the applications makefile.
# For an example take a look at the samples/linux/subscribe_publish_cpp_sample folder.
#
# So for now we will install the needed dependencies (mbedTLS) and also this SDK folder into
# the /opt/ folder for now.
#
#

debianize_install[target] = "install"
debianize_install[tdeps] = "build"
debianize_install() {
	@echo "Running install target."
	dh_testdir
	dh_testroot
	dh_clean  -k
	install -m 0755 -d debian/${BPN}/opt/aws-iot-sdk-c
	cp -r ${PPS}/certs debian/${BPN}/opt/aws-iot-sdk-c/
	cp -r ${PPS}/external_libs debian/${BPN}/opt/aws-iot-sdk-c/
	cp -r ${PPS}/include debian/${BPN}/opt/aws-iot-sdk-c/
	cp -r ${PPS}/platform debian/${BPN}/opt/aws-iot-sdk-c/
	cp -r ${PPS}/src debian/${BPN}/opt/aws-iot-sdk-c/
	cp -r ${PPS}/tests debian/${BPN}/opt/aws-iot-sdk-c/
	cp -r ${PPS}/samples debian/${BPN}/opt/aws-iot-sdk-c/
}
