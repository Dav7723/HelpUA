package com.app.helpua.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleExpandableListAdapter
import androidx.fragment.app.Fragment
import com.app.helpua.R
import com.app.helpua.databinding.FragmentSecurityPolicyBinding


class SecurityPolicyFragment : Fragment() {

    private var _binding: FragmentSecurityPolicyBinding? = null
    private val binding get() = _binding!!

    private val groups = mutableListOf<Group>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecurityPolicyBinding.inflate(layoutInflater)

        createGroups()
        initView()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


    private fun createChildList(groups: List<Group>): List<List<Map<String, String>>> {
        val result: MutableList<List<Map<String, String>>> = ArrayList()
        for (g in groups) {
            val secList: MutableList<Map<String, String>> = ArrayList()
            for (c in g.children) {
                val child: MutableMap<String, String> = HashMap()
                child["Sub Item"] = c
                secList.add(child)
            }
            result.add(secList)
        }
        return result
    }

    private fun createGroupList(groups: List<Group>): List<Map<String, String>>? {
        val result: MutableList<Map<String, String>> = java.util.ArrayList()
        for (g in groups) {
            val m: MutableMap<String, String> = java.util.HashMap()
            m["Group Item"] = g.name
            result.add(m)
        }
        return result
    }

    private fun initView() = with(binding) {
        val expListAdapter = SimpleExpandableListAdapter(
            requireActivity(),
            createGroupList(groups),
            R.layout.group_row,
            arrayOf("Group Item"),
            intArrayOf(R.id.row_name),
            createChildList(groups),
            R.layout.child_row,
            arrayOf("Sub Item"),
            intArrayOf(R.id.grp_child)
        )
        exList.apply {
            setAdapter(expListAdapter)

        }

        ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        btMap.setOnClickListener {
            startMap()
        }
    }

    private fun startMap() {
        val videoIntent = Intent(Intent.ACTION_VIEW)
        videoIntent.data =
            Uri.parse("https://www.google.com/maps/d/u/0/viewer?mid=1sELKMuMigbzEzwUhzGcREs0Dic4OkQM&femb=1&ll=51.13625943057532%2C32.89024529182605&z=6")

        startActivity(videoIntent)
    }

    private fun createGroups() {
        val webG = Group("Дії під час повітряної тривоги")
        val mapG = Group("Поради щодо дій під час евакуації")
        val telG = Group("Як діяти населенню в місцях бойових дій")
        val child4 = Group("Контакти екстрених служб")

        webG.addChild(getString(R.string.child_1) + getString(R.string.child_3_1))
        mapG.addChild(
            "Якщо ви вдома під час евакуації:\n" +
                    "- виконуйте інструкції відповідних служб;\n" +
                    "- будьте готові до того, що вам доведеться рухатися повільно, особливо якщо людей багато;\n" +
                    "- не повертайтеся за речами, що залишилися в будинку;\n" +
                    "- уникайте паніки та допомагайте іншим, особливо дітям, людям з обмеженими можливостями та літнім людям.\n\n" +
                    "Перед тим, як залишити житло, необхідно:\n" +
                    "– зачинити вікна;\n" +
                    "– вимкнути газ, воду та електрику;\n" +
                    "– забрати продукти з холодильника.\n" +
                    "- запасні ключі від квартири необхідно здати представнику житлово-експлуатаційної організації.\n\n" +
                    "Громадяни повинні мати при собі:\n" +
                    "– паспорт;\n" +
                    "– військовий квиток;\n" +
                    "– документ про освіту;\n" +
                    "– трудову книжку або пенсійне посвідчення;\n" +
                    "– свідоцтво про народження;\n" +
                    "– гроші і цінності;\n" +
                    "– продукти харчування і воду на 3 доби;\n" +
                    "– необхідний одяг і взуття загальною вагою не більш як 50 кілограмів на кожного члена сім’ї.\n" +
                    "Дітям дошкільного віку вкладається у кишеню або пришивається до одягу записка, де зазначається прізвище, ім’я та по батькові, домашня адреса, а також ім’я та по батькові матері і батька.\n\n" +

                    "Після евакуації:\n" +
                    "зібратися на місці збору, що було зазначене в плані дій;\n" +
                    "намагайтеся отримати актуальну інформацію щодо події від відповідних служб та дотримуйтесь їхніх інструкцій;\n" +
                    "будьте готові до того, що може знадобитися переселення на тимчасове місце проживання;\n" +
                    "Завжди пам'ятайте, що безпека важливіша за будь-які матеріальні речі та від цього залежить ваше життя й життя інших людей."
        )
        telG.addChild(getString(R.string.child_3))
        child4.addChild(
            "101 - пожежна охорона;\n" +
                    "\n" +
                    "102 - поліція;\n" +
                    "\n" +
                    "103 - швидка допомога;\n" +
                    "\n" +
                    "104 - аварійна служба газу.\n" +
                    "\n" +
                    "104 – Аварійна служба газової мережі\n" +
                    "\n" +

                    "109 – Довідкова служба"
        )

        groups.apply {
            add(webG)
            add(mapG)
            add(telG)
            add(child4)
        }

    }
}
