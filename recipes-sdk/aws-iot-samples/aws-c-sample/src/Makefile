#This target is to ensure accidental execution of Makefile as a bash script will not execute commands like rm in unexpected directories and exit gracefully.
.prevent_execution:
	exit 0

CC = $(TARGET_PREFIX)-g++

#remove @ for no make command prints
DEBUG = @

APP_DIR = .
APP_INCLUDE_DIRS += -I $(APP_DIR)
APP_NAME = subscribe_publish_cpp_sample
APP_SRC_FILES = $(APP_NAME).cpp

#IoT client directory
SDK_PATH = /opt/aws-iot-sdk-c

PLATFORM_DIR = $(SDK_PATH)/platform/linux/mbedtls
PLATFORM_COMMON_DIR = $(SDK_PATH)/platform/linux/common

IOT_INCLUDE_DIRS += -I $(SDK_PATH)/include
IOT_INCLUDE_DIRS += -I $(SDK_PATH)/external_libs/jsmn
IOT_INCLUDE_DIRS += -I $(PLATFORM_COMMON_DIR)
IOT_INCLUDE_DIRS += -I $(PLATFORM_DIR)

IOT_SRC_FILES += $(shell find $(SDK_PATH)/src/ -name '*.c')
IOT_SRC_FILES += $(shell find $(SDK_PATH)/external_libs/jsmn -name '*.c')
IOT_SRC_FILES += $(shell find $(PLATFORM_DIR)/ -name '*.c')
IOT_SRC_FILES += $(shell find $(PLATFORM_COMMON_DIR)/ -name '*.c')

#TLS - mbedtls (installed with apt-get libmbedtls-dev)
#MBEDTLS_DIR = /usr/lib/$(TARGET_PREFIX)
#EXTERNAL_LIBS += -L$(MBEDTLS_DIR)
#LD_FLAG += -Wl,-rpath,$(MBEDTLS_DIR)
#LD_FLAG += -ldl $(MBEDTLS_DIR)/libmbedtls.a $(MBEDTLS_DIR)/libmbedcrypto.a $(MBEDTLS_DIR)/libmbedx509.a -lpthread
LD_FLAG += -ldl -lmbedtls -lmbedcrypto -lmbedx509 -lpthread

#Aggregate all include and src directories
INCLUDE_ALL_DIRS += $(IOT_INCLUDE_DIRS)
INCLUDE_ALL_DIRS += $(TLS_INCLUDE_DIR)
INCLUDE_ALL_DIRS += $(APP_INCLUDE_DIRS)

SRC_FILES += $(APP_SRC_FILES)
SRC_FILES += $(IOT_SRC_FILES)

# Logging level control
LOG_FLAGS += -DENABLE_IOT_DEBUG
LOG_FLAGS += -DENABLE_IOT_INFO
LOG_FLAGS += -DENABLE_IOT_WARN
LOG_FLAGS += -DENABLE_IOT_ERROR

COMPILER_FLAGS += $(LOG_FLAGS)
#If the processor is big endian uncomment the compiler flag
#COMPILER_FLAGS += -DREVERSED
COMPILER_FLAGS += -std=c++0x

MAKE_CMD = $(CC) $(SRC_FILES) $(COMPILER_FLAGS) -o $(APP_NAME) $(LD_FLAG) $(EXTERNAL_LIBS) $(INCLUDE_ALL_DIRS)

all:
	$(PRE_MAKE_CMD)
	$(DEBUG)$(MAKE_CMD)
	$(POST_MAKE_CMD)

clean:
	rm -f $(APP_DIR)/$(APP_NAME)
	$(MBED_TLS_MAKE_CMD) clean
