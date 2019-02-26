package com.ctrlappz.otlob.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.utils.ProfileInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        val profileInfo = ProfileInfo(context)
        rootView.nameTV.text = profileInfo.getInformation("name")
        rootView.addressTV.text = profileInfo.getInformation("address")
        rootView.phoneTV.text = profileInfo.getInformation("phone")
        rootView.hoursTV.text = profileInfo.getInformation("hours")
        rootView.bioTV.text = profileInfo.getInformation("bio")
        Picasso.get().load(profileInfo.getInformation("profile")).into(rootView.circleImageView)
        return rootView
    }

}
