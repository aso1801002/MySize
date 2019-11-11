package jp.ac.asojuku.mysize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.*
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_height.*

class HeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)
        //ラジオボタンの処理を実装する
        //グループに選択されたときに反応するコールバックメソッドを待機するリスナーを設定
        radioGroup.setOnCheckedChangeListener{
            //2つの引数(第一引数ラジオボタングループ),第二引数選択されたラジオボタンのIDを受け取って実行する処理
            gruop,checkedId ->
            //身長ラベルを上書き(ラジオグループの選ばれた　IDボタンのtext属性の値で上書き)
            height.text=findViewById<RadioButton>(checkedId).text
        }
    }
    //再表示の時に呼ばれるライフサイクルのコールバックメソッドのonresumeをオーバーライド

    override fun onResume() {
        super.onResume()
        //spinnerにitem（選択肢）が呼ばれた時のコールバックメソッドを設定
        spinner.onItemSelectedListener= //spinnerにアイテムを運んだ時の動きを持ったクラスの匿名インスタンスを挿入
            object:AdapterView.OnItemSelectedListener{ //アイテムを運んだ時の動きを持ったクラスの意味クラスをを定義して匿名インスタンスにする
                override fun onItemSelected
                            ( //アイテムを運んだ時の処理
                    parent: AdapterView<*>?, //選択肢が発生したビュー（スピナーのこと）
                    view: View?, //選択されたビュー（アイテムのこと）
                    position: Int, //選んだ選択肢が何番目か？
                    id: Long //選択されたアイテムのid
                ) {
                    //選択肢を取得するためにスピナーのインスタンスを取得する
                    val spinner =parent as? Spinner
                    //選択肢（今回は１６０などの文字列）を取得
                    val item =spinner?.selectedItem as? String
                    //取得した値を身長の値のテキストビューに上書きする
                    item?.let{
                        if(it.isNotEmpty()){
                            height.text = it //itつまり身長の値が空文字でなければ身長のテキストビュー（heoght）に代入
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //itemuが選ばれなかった場合何もしない
                }
            }
        //シークバーの処理を定義する
        //共有プリファレンスから身長設定値を取得する(シークバーの表示値のため)
       val pref= PreferenceManager.getDefaultSharedPreferences(this)
        val heightVal=pref.getInt("HEIGHT",160)//身長を変数に保存
        height.text=heightVal.toString() //身長ラベルの値もこの取得値で上書き
        //シークバーの現在値(progress)も取得値で上書き
        seekBar.progress=heightVal
        //シークバーの値が変更されたらこーるばっくされるメソッドを持つ
        //匿名クラスのインスタンスを引き渡す
        seekBar.setOnSeekBarChangeListener(
            object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar:  SeekBar?, //値が変化したシークバーのインスタンス
                    progress: Int, //値が変化したシークバーの現在地
                    fromUser: Boolean//ユーザーが操作したか
                ) {
                    height.text=progress.toString() //ユーザーの指定値で「身長の表示を変える」
                }
                //2つめのoverrideメソッド
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                //ここでは今回何もしない
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

    }
    //画面が閉じられるときに呼ばれるコールバックメソッドをオーバーライド
    override fun onPause() {
        super.onPause()
        //身長の現在地を共有プリファレンスに保存する処理を実装
        //共有プリファレンスのインスタンスを取得
        val pref=PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit{
            //身長ラベルの表示値を取得してStringに変換したのち、Intに変換して実装
            this.putInt("HEIGHT",height.text.toString().toInt());
        }
    }
}
