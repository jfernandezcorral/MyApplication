package com.example.myapplication

//import android.icu.text.SimpleDateFormat
//import android.view.View
//import android.webkit.WebSettings
//import androidx.webkit.ProxyConfig
//import androidx.webkit.ProxyController
//import kotlinx.coroutines.delay
//import java.io.*

import android.app.DownloadManager
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.webkit.ProxyConfig
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.concurrent.Executor


//import java.nio.charset.Charset
//import java.util.*
//import java.util.concurrent.Executor
//import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private class MyWebViewClient : WebViewClient() {
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler,
            error: SslError?
        ) { // $hasResult
            handler.proceed()
        }
        //private  var df = SimpleDateFormat("E, dd MMM yyyy kk:mm:ss", Locale.US)
        /*@TargetApi(21)
        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
            if (request.method.equals("GET") && request.url.toString().startsWith("https://spadev-opfcdn")){
                    val url = request.url.toString()
                    val urlConnection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
                   try {
                        var headers =
                            urlConnection.headerFields.mapValues { it.value.joinToString() }
                        headers = headers.toMutableMap()
                        headers["Access-Control-Allow-Origin"] = "*"
                        headers["Access-Control-Allow-Headers"] = "*"
                        headers["Access-Control-Allow-Credentials"] = "true"


                        /*val outputStream = urlConnection.outputStream
                        val inStream = PipedInputStream()
                        Thread {
                            while (outputStream.)
                        }.start()*/

                        // val string = "hello world :)"
                        //val inStream = String().byteInputStream(charset = Charset.forName("UTF-8")) // Works when used as stream, sends "hello world :)" as response

                        return WebResourceResponse(
                            headers["Content-Type"],
                            if (urlConnection.contentEncoding === null) "UTF-8" else urlConnection.contentEncoding,
                            urlConnection.responseCode,
                            "OK",
                            headers,
                            urlConnection.inputStream
                        )
                    } finally {
                        //Thread {
                        //    Thread.sleep(4000)
                            urlConnection.disconnect()
                        //}.start()


                    }

            }
            return null
            /*if (request.url.toString().startsWith("https://spadev-opfcdn"))
            {
                request.requestHeaders["Access-Control-Allow-Origin"] = "*";
            }
            val pp = super.shouldInterceptRequest(view, request)
            return pp*/
            /*if (request.method.equals("OPTIONS", true)) {
                return this.build()
            }
            return null*/
        }*/
        /*@TargetApi(21)
        fun build( ): WebResourceResponse {
            val dateString = this.df.format(Date())
            val headers = HashMap<String,String> ()
            headers["Connection"] = "close"
            headers["Content-Type"] = "text/plain"
            headers["Date"] = "$dateString GMT"
            headers["Access-Control-Allow-Origin"] = "*"
            headers["Access-Control-Allow-Methods"] = "GET, POST, DELETE, PUT, OPTIONS"
            headers["Access-Control-Max-Age"] = "600"
            headers["Access-Control-Allow-Credentials"] = "true"
            headers["Access-Control-Allow-Headers"] = "accept, authorization, Content-Type"
            headers["Via"] = "1.1 velour"
            return WebResourceResponse("text/plain", "UTF-8", 200, "OK", headers, null)
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.toolbar)

        /*val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)*/

        val proxyConfig = ProxyConfig.Builder()
            .addProxyRule("192.168.1.143:8081")
            /*.addDirect()*/.build()
        val executor = Executor { }
        val listener = Runnable { }
        //ProxyController.getInstance().setProxyOverride(proxyConfig, executor, listener)
        WebView.setWebContentsDebuggingEnabled(true)
        val myWebView: WebView = findViewById(R.id.web)
        myWebView.settings.domStorageEnabled = true
        myWebView.webViewClient = MyWebViewClient()
        myWebView.settings.javaScriptEnabled = true
        myWebView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val request = DownloadManager.Request(
                Uri.parse(url)
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "name of your file"
            )
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            //you could show a message here
        }

        //myWebView.loadUrl("https://tst.tf7.lacaixa.es/absis3/showcase")
        myWebView.loadUrl("http://192.168.1.222:3000")

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}