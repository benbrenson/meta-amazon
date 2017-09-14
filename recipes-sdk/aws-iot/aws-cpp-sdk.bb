DESCRIPTION = "Amazon cloud services SDK for IoT based applications in C++."
LICENSE = "gpl2"

inherit dpkg debianize
DEB_DEPENDS = "libssl1.0.2 libssl-dev"

URL="git://github.com/aws/aws-iot-device-sdk-cpp.git"
BRANCH="master"
SRCREV = "63e8ce25e6b282719d112f3f58966cc1e78d271b"

SRC_DIR = "git"
SRC_URI += "${URL};branch=${BRANCH};protocol=https \
            file://debian \
           "

SECTION = "utils"
PRIORITY = "optional"

CMAKE_CXX_FLAGS="-Wno-error=deprecated-declarations"

#
# Prepare build folder, since project doesn't support in-source builds
#
do_build_prepend() {
	mkdir ${PPS}/build
}

debianize_build[target] = "build"
debianize_build() {
	@echo "Running build target."

	# Build static lib
	cd ${PPS}/build ; cmake -DCMAKE_CXX_FLAGS=${CMAKE_CXX_FLAGS} ../ ; make -j${PARALLEL_MAKE}

	# Build shared lib
	cd ${PPS}/build ; cmake -DCMAKE_CXX_FLAGS=${CMAKE_CXX_FLAGS} -DBUILD_SHARED_LIBRARY=true ../ ; make -j${PARALLEL_MAKE}
}

debianize_install[target] = "install"
debianize_install[tdeps] = "build"
debianize_install() {
	@echo "Running install target."
	dh_testdir
	dh_testroot
	dh_clean  -k
	install -m 0755 -d debian/${BPN}/usr/lib/${TARGET_PREFIX}
	install -m 0644 ${PPS}/lib/libaws-iot-sdk-cpp.so debian/${BPN}/usr/lib/${TARGET_PREFIX}/
	install -m 0644 ${PPS}/archive/libaws-iot-sdk-cpp.a debian/${BPN}/usr/lib/${TARGET_PREFIX}/
}
