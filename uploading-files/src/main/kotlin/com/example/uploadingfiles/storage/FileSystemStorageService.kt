package com.example.uploadingfiles.storage

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream

@Service
class FileSystemStorageService(
    private val properties: StorageProperties
) : StorageService {

    private val rootLocation: Path = Paths.get(properties.location)

    override fun init() {
        runCatching { Files.createDirectories(rootLocation) }
            .onFailure { throw StorageException("Could not initialize storage", it) }
    }

    override fun store(file: MultipartFile) {
        val filename = StringUtils.cleanPath(requireNotNull(file.originalFilename))
        if (file.isEmpty) {
            throw StorageException("Failed to store empty file $filename")
        }

        if (filename.contains("..")) {
            throw StorageException("Cannot store file with relative path outside current directory $filename")
        }

        runCatching {
            file.inputStream.use {
                Files.copy(it, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING)
            }
        }.onFailure { throw StorageException("Failed to store file $filename", it) }

    }

    override fun loadAll(): Stream<Path> = runCatching {
        Files.walk(this.rootLocation, 1)
            .filter { it != this.rootLocation }
            .map { this.rootLocation.relativize(it) }
    }.getOrElse { throw StorageException("Failed to read stored files", it) }


    override fun load(filename: String): Path = rootLocation.resolve(filename)

    override fun loadAsResource(filename: String): Resource {
        val file = load(filename)
        val resource = runCatching { UrlResource(file.toUri()) }
            .getOrElse { throw StorageFileNotFoundException("Could not read file: $filename", it) }
        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw StorageException("Could not read file: $filename")
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }
}