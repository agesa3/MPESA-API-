package com.example.mpesaapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var daraja: Daraja

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        daraja = Daraja.with(
                "7OjbUyrmJZwgryqzWFaitWRVCzXIlPAZ",
                "JE8jGA6BsvMwPMKE",
                Env.SANDBOX, //for Test use Env.PRODUCTION when in production
                object : DarajaListener<AccessToken> {
                    override fun onResult(accessToken: AccessToken) {

                        Toast.makeText(this@MainActivity, "MPESA TOKEN : ${accessToken.access_token}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(error: String) {

                    }
                })

        button.setOnClickListener {
            val phoneNumber = phone.text.trim().toString().trim()
            val lnmExpress = LNMExpress(
                    "174379",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                    TransactionType.CustomerPayBillOnline,
                    "1",
                    phoneNumber,
                    "174379",
                    phoneNumber,
                    "",
                    "001ABC",
                    "Goods Payment"
            )

            daraja.requestMPESAExpress(lnmExpress,
                    object : DarajaListener<LNMResult> {
                        override fun onResult(lnmResult: LNMResult) {

                            Toast.makeText(this@MainActivity, "Response here ${lnmResult.ResponseDescription}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(error: String) {

                            Toast.makeText(this@MainActivity, "Error here $error", Toast.LENGTH_SHORT).show()
                        }
                    }
            )
        }

    }
}