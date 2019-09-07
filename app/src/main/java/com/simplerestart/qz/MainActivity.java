package com.simplerestart.qz;

import android.content.*;
import android.os.Process;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import android.media.*;
import android.widget.MediaController.*;
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	private Button restart, systemui, recovery, about, shutdown;
	private SoundPool mysounds;
	@Override
	public void onClick(View p1)
	{
		try{
		switch(p1.getId()){
			case R.id.mainButton1:
				pesan("Restart");
				commands.Restart();
				break;
			case R.id.mainButton2:
				pesan("Restart SystemUi");
				commands.restartsystemui();
				break;
			case R.id.mainButton3:
				pesan("Recovery Mode");
				commands.recovermodes();
				break;
			case R.id.mainButton4:
				dialogs("==== [ Pembuat ] ====", "Tools by Qiuby Zhukhi")
				.create()
				.show();
				break;
			case R.id.mainButton5:
				pesan("ShutDown");
				commands.shutdown();
				break;}
		siap();
		}catch(Exception e){
			uh();
			pesan(e.toString());
		}
	}
	public void pesan(String p){
		Toast.makeText(getApplicationContext(), p, Toast.LENGTH_SHORT).show();
	}
	public AlertDialog.Builder dialogs(String title, String pesan){
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setCancelable(true);
		build.setTitle(title);
		build.setMessage(pesan);
		return build;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		restart = (Button) findViewById(R.id.mainButton1);
		restart.setOnClickListener(this);
		systemui = (Button) findViewById(R.id.mainButton2);
		systemui.setOnClickListener(this);
		recovery = (Button) findViewById(R.id.mainButton3);
		recovery.setOnClickListener(this);
		about = (Button) findViewById(R.id.mainButton4);
		about.setOnClickListener(this);
		shutdown = (Button) findViewById(R.id.mainButton5);
		shutdown.setOnClickListener(this);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			AudioAttributes atribut = new AudioAttributes.Builder()
			.setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
			.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
			.build();
			this.mysounds = new SoundPool.Builder()
			.setMaxStreams(10)
			.setAudioAttributes(atribut)
			.build();
		}else{
			this.mysounds = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
		}
    }
	public void uh(){
		play(this.mysounds.load(this, R.raw.uh, 1));
	}
	public void siap(){
		play(this.mysounds.load(this, R.raw.siap,1));
	}
	public void play(int sound){
		mysounds.play(sound,1,1,0,0,1);
	}
	@Override
	public void onBackPressed()
	{
		dialogs("Exit", "Do you Want to Exit ?")
			.setNegativeButton("Exit", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					mysounds.release();
					finish();
				}
			})
			.create()
			.show();
	}
}
class commands{
	private static Runtime runtime;
	private static String shutdown = "su -c svc power shutdown";
	private static String restart = "su -c reboot";
	private static String restartsystemui = "su -c pkill -f com.android.systemui";
	private static String recoverymode = "su -c reboot recovery";
	
	public static void Restart() throws IOException{
		runtime.getRuntime().exec(restart.split(" "));
	}
	public static void restartsystemui() throws IOException{
		runtime.getRuntime().exec(restartsystemui.split(" "));
	}
	public static void recovermodes() throws IOException{
		runtime.getRuntime().exec(recoverymode.split(" "));
	}
	public static void shutdown() throws IOException{
		runtime.getRuntime().exec(shutdown.split(" "));
	}
	public static void manual(String[] c) throws IOException{
		runtime.getRuntime().exec(c);
	}
}
