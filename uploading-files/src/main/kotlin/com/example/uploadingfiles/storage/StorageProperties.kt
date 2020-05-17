package com.example.uploadingfiles.storage

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("storage")
data class StorageProperties(
    val location: String = "uploading-files/upload-dir"
)