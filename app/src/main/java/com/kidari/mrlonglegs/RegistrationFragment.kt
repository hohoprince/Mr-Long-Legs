package com.kidari.mrlonglegs

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.database.FirebaseDatabase
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.fragment_registration.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNREACHABLE_CODE")
class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var selectedItem: String? = ""
        val db = FirebaseFirestore.getInstance()
        val errand = Errand() // Errand 데이터 클래스를 연결


        val spnFirst: Spinner = view.findViewById(R.id.spnFirst)
        val spnSecond: Spinner = view.findViewById(R.id.spnSecond)
        firstNumberPicker.minValue = 1
        firstNumberPicker.maxValue = 12
        secondNumberPicker.minValue = 1
        secondNumberPicker.maxValue = 31
        thirdNumberPicker.minValue = 0
        thirdNumberPicker.maxValue = 23
        fourthNumberPicker.minValue = 0
        fourthNumberPicker.maxValue = 59
        fifthNumberPicker.minValue = 1
        fifthNumberPicker.maxValue = 12
        sixthNumberPicker.minValue = 1
        sixthNumberPicker.maxValue = 31

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.spinner_region,
                android.R.layout.simple_spinner_item
            )
                .also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnFirst.adapter = arrayAdapter
                }

        }

        spnFirst.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedItem = p0?.getItemAtPosition(p2).toString()
                if (selectedItem == "서울특별시") {
                    selectedRegion(R.array.spinner_region_seoul.toString())
                    return
                } else if (selectedItem == "부산광역시") {
                    selectedRegion(R.array.spinner_region_busan.toString())
                } else if (selectedItem == "대구광역시") {
                    selectedRegion(R.array.spinner_region_daegu.toString())
                } else if (selectedItem == "인천광역시") {
                    selectedRegion(R.array.spinner_region_incheon.toString())
                } else if (selectedItem == "광주광역시") {
                    selectedRegion(R.array.spinner_region_gwangju.toString())
                } else if (selectedItem == "대전광역시") {
                    selectedRegion(R.array.spinner_region_daejeon.toString())
                } else if (selectedItem == "울산광역시") {
                    selectedRegion(R.array.spinner_region_ulsan.toString())
                } else if (selectedItem == "세종특별자치시") {
                    selectedRegion(R.array.spinner_region_sejong.toString())
                } else if (selectedItem == "경기도") {
                    selectedRegion(R.array.spinner_region_gyeonggi.toString())
                } else if (selectedItem == "강원도") {
                    selectedRegion(R.array.spinner_region_gangwon.toString())
                } else if (selectedItem == "충청북도") {
                    selectedRegion(R.array.spinner_region_chung_buk.toString())
                } else if (selectedItem == "충청남도") {
                    selectedRegion(R.array.spinner_region_chung_nam.toString())
                } else if (selectedItem == "전라북도") {
                    selectedRegion(R.array.spinner_region_jeon_buk.toString())
                } else if (selectedItem == "전라남도") {
                    selectedRegion(R.array.spinner_region_jeon_nam.toString())
                } else if (selectedItem == "경상북도") {
                    selectedRegion(R.array.spinner_region_gyeong_buk.toString())
                } else if (selectedItem == "경상남도") {
                    selectedRegion(R.array.spinner_region_gyeong_nam.toString())
                } else if (selectedItem == "제주특별자치도") {
                    selectedRegion(R.array.spinner_region_jeju.toString())
                }
                return
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")

            }
        }
        registration_button_regist.setOnClickListener {
            //요청하기 버튼이 눌렸을때 동작
            val database = FirebaseDatabase.getInstance()
            var phonenumber: String = registration_edit_phoneNum.text.toString()
            var title: String = registration_edit_title.text.toString()
            var location: String = selectedItem.toString() + registration_edit_detailAddress.text.toString() + registration_edit_address.text.toString()
            var content : String = registration_edit_content.text.toString()
            // 등록화면에서 입력한 값들을 저장하기 위한 변수

            db.collection("심부름").document("$phonenumber").set(errand)
            // 데이터베이스 심부름에 연결

            val sendlocation =db.collection("심부름").document("$phonenumber")
            val sendcontent =db.collection("심부름").document("$phonenumber")
            val sendphonenumber =db.collection("심부름").document("$phonenumber")
            val sendtitle =db.collection("심부름").document("$phonenumber")
            // 각 변수를 데이터베이스의 심부름 Collection 안에 있는 phonenumber Document에 연결

            sendtitle.update("name", "$title")
            sendphonenumber.update("phoneNumber", "$phonenumber")
            sendcontent.update("content", "$content")
            sendlocation.update("location", "$location")
            //변수를 업데이트 하는 코드드
        }
    }

    private fun selectedRegion(region: String) {
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                region.toInt(),
                android.R.layout.simple_spinner_item
            )
                .also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnSecond.adapter = arrayAdapter
                }

        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    // 카메라 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ), REQUEST_IMAGE_CAPTURE
        )
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (context?.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.CAMERA
            )
        } ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context!!,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(
                "TAG",
                "Permission: " + permissions[0] + "was " + grantResults[0] + "카메라 허가 받음 예이^^"
            )
        } else {
            Log.d("TAG", "카메라 허가 못받음 ㅠ 젠장!!")
        }
    }
}