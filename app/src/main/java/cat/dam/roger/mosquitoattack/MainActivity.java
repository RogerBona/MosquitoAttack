package cat.dam.roger.mosquitoattack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Declarem el tamany de la pentalla
    private int layoutWidth;
    private int layoutHeight;

    //Creem el handler i el timmer per fer el contador
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private Timer timersegons = new Timer();

    ImageView iv_mosquit;
    AnimationDrawable mosquit_animat;
    ConstraintLayout main_screen;
    Button button_start;
    TextView contador, tv_puntuacio;

    float iv_mosquitX;
    float iv_mosquitY;

    int puntuacio = 0;
    boolean mort = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mosquit_animat = new AnimationDrawable();
        // Carrega el ImageView que contindrà l'animació i actualiza el fons d'imatge amb el recurs XML on es defineix les imatges
        // i temps d'animació del mosquit
        iv_mosquit = (ImageView) findViewById(R.id.iv_mosquit);
        button_start = (Button) findViewById(R.id.button_start);
        contador = (TextView) findViewById(R.id.contador);
        tv_puntuacio = (TextView) findViewById(R.id.tv_puntuacio);

        // Situem la imatge en la pantalla
        iv_mosquit.setX(100);
        iv_mosquit.setY(150);
        iv_mosquit.setBackgroundResource(R.drawable.mosquit_animat);
        // Obté el fons que ha estat compilat amb un objecte AnimationDrawable
        mosquit_animat = (AnimationDrawable) iv_mosquit.getBackground();
        // Comença l'animació (per defecte repetició de cicle).
        mosquit_animat.start();

        // Aqui agafarem el tamany del layout i el posarem a les variables de layoutW i H
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        layoutWidth = size.x;
        layoutHeight = size.y;

        // Posem el mosquit a fora la pantalla
        iv_mosquit.setX(-80.0f);
        iv_mosquit.setY(-80.0f);


        //Funcio on iniciarem el programa
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresiu();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(contador.getText().equals("0")){
                                    contador.setText("30");
                                    tv_puntuacio.setText("0");
                                }else {
                                    mouremosquit();
                                }
                            }
                        });
                    }
                }, 0, 10);
            }
        });

    }

    //Funcio de canvi d'animaci oquen apratem el mosquit

    public void mosquitapretat(View view) {

        // En cas de que es cliqui el mosquit actualiza el fons d'imatge amb el recurs XML on es defineix les imatges
        // i temps d'animació de la taca de sang
        iv_mosquit.setBackgroundResource(R.drawable.sang_animat);
        mosquit_animat = (AnimationDrawable) iv_mosquit.getBackground();
        // Executara l'annimacio
        mosquit_animat.start();

        //Posarem el boolea de mort a false que voldra dir que esta mort
        mort = false;
        //Sumarem 1 al contador de morts del mosquit
        puntuacio = Integer.parseInt(tv_puntuacio.getText().toString());
        puntuacio++;
        tv_puntuacio.setText(String.valueOf(puntuacio));

        regenerarmosquit();
    }

    public void regenerarmosquit() {

        //Cada cop que el mosquit es mogui mirara si esta mort o no en cas de que ho estigui esperara 1 segon i el reviura
        if (mort == false) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    iv_mosquit.setBackgroundResource(R.drawable.mosquit_animat);
                    // Obté el fons que ha estat compilat amb un objecte AnimationDrawable
                    mosquit_animat = (AnimationDrawable) iv_mosquit.getBackground();
                    // Comença l'animació (per defecte repetició de cicle).
                    mosquit_animat.start();
                }
            }, 2000);
        }

    }

    // Aqui farem que el mosquit es mogui cap a d'alt
    public void mouremosquit() {

        iv_mosquitY -= 10;
        if (iv_mosquit.getY() + iv_mosquit.getHeight() < 0) {
            iv_mosquitX = (float) Math.floor(Math.random() * (layoutWidth - iv_mosquit.getWidth()));
            iv_mosquitY = layoutHeight + 100.0f;
        }
        iv_mosquit.setX(iv_mosquitX);
        iv_mosquit.setY(iv_mosquitY);
    }

    //Funcio on fem la conta regresiva fins a 0
    public void regresiu() {

        timersegons.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int numero = Integer.parseInt(contador.getText().toString());
                        numero--;
                        String numseg = String.valueOf(numero);
                        contador.setText(numseg);
                    }
                });
            }
        }, 1000, 1000);
    }
}