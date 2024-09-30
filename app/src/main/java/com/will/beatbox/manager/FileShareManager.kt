package com.will.beatbox.manager

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class FileShareManager(private val context: Context) {

    fun createAndShareFile(fileName: String, content: String, mimeType: String) {
        // 创建文件
        val file = createFile(fileName, content)

        // 获取文件的 content:// URI
        val fileUri = getUriForFile(file)

        // 创建分享 Intent
        val shareIntent = createShareIntent(fileUri, mimeType)

        // 启动分享
        context.startActivity(Intent.createChooser(shareIntent, "Share File"))
    }

    private fun createFile(fileName: String, content: String): File {
        // 使用 getFilesDir() 获取内部存储目录
        // 目录 path 是在 /data/data/ 包名 / files/ documents
        val directory = File(context.getFilesDir(), "documents")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, fileName)
        file.writeText(content)
        return file
    }

    private fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    private fun createShareIntent(fileUri: Uri, mimeType: String): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}