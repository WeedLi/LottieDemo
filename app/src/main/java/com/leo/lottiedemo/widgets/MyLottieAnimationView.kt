package com.leo.lottiedemo.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.util.JsonReader
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieTask
import java.io.File
import java.io.FileReader
import java.net.HttpURLConnection
import java.net.URL


class MyLottieAnimationView : LottieAnimationView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //可以随便定义路径，发现不设置会报异常
        imageAssetsFolder = "/images";
    }

    /**
     * 从本地文件加载动画
     * @param absolutePath: 文件的绝对路径 xx/xxx.json
     * @param cacheKey: 缓存的key
     */
    fun setAnimationFromLocal(absolutePath: String, cacheKey: String) {
        val file = java.io.File(absolutePath)
        setAnimationFromLocal(file, cacheKey)
    }

    /**
     * 从本地文件加载动画
     * @param file: 文件
     * @param cacheKey: 缓存的key
     */
    fun setAnimationFromLocal(file: File, cacheKey: String) {
        val fileReader = FileReader(file)
        val jsonReader = JsonReader(fileReader)
        super.setAnimation(file.inputStream(), cacheKey)
    }

    /**
     * 动态更新动画里的图片
     * @param id:  对应的id 问UI大佬定的是多少
     * @param bitmap:
     */
    override fun updateBitmap(id: String?, bitmap: Bitmap?): Bitmap? {
        return super.updateBitmap(id, bitmap)
    }

    /**
     * 动态更新动画里的图片
     * @param id:  对应的id 问UI大佬定的是多少
     * @param url: 图片链接
     */
    fun setDynamicImage(id: String, url: String) {
        val handler = handler
        LottieTask.EXECUTOR.execute {
            (URL(url).openConnection() as? HttpURLConnection)?.let {
                try {
                    it.connectTimeout = 20 * 1000
                    it.requestMethod = "GET"
                    it.connect()
                    it.inputStream.use { stream ->
                        BitmapFactory.decodeStream(stream)?.let {
                            handler.post {
                                updateBitmap(id, it)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        it.disconnect()
                    } catch (disconnectException: Throwable) {
                        // ignored here
                    }
                }
            }
        }
    }

}