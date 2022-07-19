package com.example.btschach.viewController;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.btschach.R;
import com.example.btschach.bluetooth.BluetoothConnectionService;
import com.example.btschach.controller.ChessGame;
import com.example.btschach.model.Chesspiece;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.SerializationUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

public class NewGameActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ImageView bs00;private ImageView bs01;private ImageView bs02;private ImageView bs03;private ImageView bs04;private ImageView bs05;private ImageView bs06;private ImageView bs07;
    private ImageView bs10;private ImageView bs11;private ImageView bs12;private ImageView bs13;private ImageView bs14;private ImageView bs15;private ImageView bs16;private ImageView bs17;
    private ImageView bs20;private ImageView bs21;private ImageView bs22;private ImageView bs23;private ImageView bs24;private ImageView bs25;private ImageView bs26;private ImageView bs27;
    private ImageView bs30;private ImageView bs31;private ImageView bs32;private ImageView bs33;private ImageView bs34;private ImageView bs35;private ImageView bs36;private ImageView bs37;
    private ImageView bs40;private ImageView bs41;private ImageView bs42;private ImageView bs43;private ImageView bs44;private ImageView bs45;private ImageView bs46;private ImageView bs47;
    private ImageView bs50;private ImageView bs51;private ImageView bs52;private ImageView bs53;private ImageView bs54;private ImageView bs55;private ImageView bs56;private ImageView bs57;
    private ImageView bs60;private ImageView bs61;private ImageView bs62;private ImageView bs63;private ImageView bs64;private ImageView bs65;private ImageView bs66;private ImageView bs67;
    private ImageView bs70;private ImageView bs71;private ImageView bs72;private ImageView bs73;private ImageView bs74;private ImageView bs75;private ImageView bs76;private ImageView bs77;

    private ImageView[][] boardSpaces;
    private ChessGame chessGame;
    private boolean isPlayerHost;

    private TextView tvPlayerTurn;

    @Override
    public void onBackPressed() {/* do nothing*/}

    public void buttonMainMenu(View view) {
        finish();
    }

    public void buttonForfeit(View view) {
        String forfeit = "Forfeit";
        byte[] bytes = forfeit.getBytes(Charset.defaultCharset());
        mBluetoothConnection.write(bytes);

        try {
            new AlertDialog.Builder(this)
                    .setMessage("Verloren!" + System.lineSeparator() + "Das Spiel wurde aufgegeben.")
                    .setPositiveButton("Neues Spiel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int dialogInt) {
                            chessGame = new ChessGame();
                            byte[] data = SerializationUtils.serialize(chessGame);
                            mBluetoothConnection.write(data);
                            updateBoard();
                        }
                    })
                    .setNegativeButton("Zurück zum Hauptmenü", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int dialogInt) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create().show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public void buttonDraw(View view) {
        String offerDraw = "OfferDraw";
        byte[] bytes = offerDraw.getBytes(Charset.defaultCharset());
        mBluetoothConnection.write(bytes);
        View parentLayout = findViewById(android.R.id.content);
        message = Snackbar.make(parentLayout, "Patt-Anfrage an Gegner geschickt.", Snackbar.LENGTH_SHORT);
        message.show();
    }

    public void updateBoard() {
        if (chessGame.getCurrentPlayer()) {
            tvPlayerTurn.setText("Spieler am Zug: Weiß");
        } else {
            tvPlayerTurn.setText("Spieler am Zug: Schwarz");
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String spaceState = (String) chessGame.getChessboard()[i][j].get(1);
                Chesspiece chesspiece = (Chesspiece) chessGame.getChessboard()[i][j].get(0);
                if (spaceState == null) {
                    boardSpaces[i][j].setBackgroundTintList(null);
                } else {
                    switch (spaceState) {
                        case "blue":
                            boardSpaces[i][j].setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.chess_piece_selected, null)));
                            break;
                        case "green":
                            boardSpaces[i][j].setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.chess_movable_space, null)));
                            break;
                        case "red":
                            boardSpaces[i][j].setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.chess_piece_captureable, null)));
                            break;
                    }
                }
                if (chesspiece == null) {
                    boardSpaces[i][j].setImageResource(0);
                } else {
                    switch (chesspiece.getName()) {
                        case "Pawn" :
                            if (chesspiece.getColor().equals("black")) {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_pawn_black);
                            } else {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_pawn_white);
                            } break;
                        case "Rook" :
                            if (chesspiece.getColor().equals("black")) {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_rook_black);
                            } else {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_rook_white);
                            } break;
                        case "Knight" :
                            if (chesspiece.getColor().equals("black")) {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_knight_black);
                            } else {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_knight_white);
                            } break;
                        case "Bishop" :
                            if (chesspiece.getColor().equals("black")) {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_bishop_black);
                            } else {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_bishop_white);
                            } break;
                        case "Queen" :
                            if (chesspiece.getColor().equals("black")) {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_queen_black);
                            } else {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_queen_white);
                            } break;
                        case "King" :
                            if (chesspiece.getColor().equals("black")) {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_king_black);
                            } else {
                                boardSpaces[i][j].setImageResource(R.drawable.chess_king_white);
                            } break;
                    }
                }
            }
        }
    }

    private void boardSpaceClicked(int i, int j) {
        if (isPlayerHost == chessGame.getCurrentPlayer()) {
            chessGame.selectBoardSpace(i,j, isPlayerHost, true);
            updateBoard();

            byte[] data = SerializationUtils.serialize(chessGame);
            mBluetoothConnection.write(data);

            if (chessGame.getVictory() != null) {
                String message = "Fehler";
                if (chessGame.getVictory().equals("victory")) {
                    message = "Gewonnen!";
                    String gameLost = "GameLost";
                    byte[] bytes = gameLost.getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                }
                if (chessGame.getVictory().equals("draw")) {
                    message = "Patt!";
                    String gameLost = "GameDraw";
                    byte[] bytes = gameLost.getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                }
                try {
                    new AlertDialog.Builder(this)
                            .setMessage(message)
                            .setPositiveButton("Neues Spiel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int dialogInt) {
                                    chessGame = new ChessGame();
                                    byte[] data = SerializationUtils.serialize(chessGame);
                                    mBluetoothConnection.write(data);
                                    updateBoard();
                                }
                            })
                            .setNegativeButton("Zurück zum Hauptmenü", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int dialogInt) {
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .create().show();
                } catch (WindowManager.BadTokenException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void boardSpace00(View view) {boardSpaceClicked(0, 0);}
    public void boardSpace01(View view) {boardSpaceClicked(0, 1);}
    public void boardSpace02(View view) {boardSpaceClicked(0, 2);}
    public void boardSpace03(View view) {boardSpaceClicked(0, 3);}
    public void boardSpace04(View view) {boardSpaceClicked(0, 4);}
    public void boardSpace05(View view) {boardSpaceClicked(0, 5);}
    public void boardSpace06(View view) {boardSpaceClicked(0, 6);}
    public void boardSpace07(View view) {boardSpaceClicked(0, 7);}

    public void boardSpace10(View view) {boardSpaceClicked(1, 0);}
    public void boardSpace11(View view) {boardSpaceClicked(1, 1);}
    public void boardSpace12(View view) {boardSpaceClicked(1, 2);}
    public void boardSpace13(View view) {boardSpaceClicked(1, 3);}
    public void boardSpace14(View view) {boardSpaceClicked(1, 4);}
    public void boardSpace15(View view) {boardSpaceClicked(1, 5);}
    public void boardSpace16(View view) {boardSpaceClicked(1, 6);}
    public void boardSpace17(View view) {boardSpaceClicked(1, 7);}

    public void boardSpace20(View view) {boardSpaceClicked(2, 0);}
    public void boardSpace21(View view) {boardSpaceClicked(2, 1);}
    public void boardSpace22(View view) {boardSpaceClicked(2, 2);}
    public void boardSpace23(View view) {boardSpaceClicked(2, 3);}
    public void boardSpace24(View view) {boardSpaceClicked(2, 4);}
    public void boardSpace25(View view) {boardSpaceClicked(2, 5);}
    public void boardSpace26(View view) {boardSpaceClicked(2, 6);}
    public void boardSpace27(View view) {boardSpaceClicked(2, 7);}

    public void boardSpace30(View view) {boardSpaceClicked(3, 0);}
    public void boardSpace31(View view) {boardSpaceClicked(3, 1);}
    public void boardSpace32(View view) {boardSpaceClicked(3, 2);}
    public void boardSpace33(View view) {boardSpaceClicked(3, 3);}
    public void boardSpace34(View view) {boardSpaceClicked(3, 4);}
    public void boardSpace35(View view) {boardSpaceClicked(3, 5);}
    public void boardSpace36(View view) {boardSpaceClicked(3, 6);}
    public void boardSpace37(View view) {boardSpaceClicked(3, 7);}

    public void boardSpace40(View view) {boardSpaceClicked(4, 0);}
    public void boardSpace41(View view) {boardSpaceClicked(4, 1);}
    public void boardSpace42(View view) {boardSpaceClicked(4, 2);}
    public void boardSpace43(View view) {boardSpaceClicked(4, 3);}
    public void boardSpace44(View view) {boardSpaceClicked(4, 4);}
    public void boardSpace45(View view) {boardSpaceClicked(4, 5);}
    public void boardSpace46(View view) {boardSpaceClicked(4, 6);}
    public void boardSpace47(View view) {boardSpaceClicked(4, 7);}

    public void boardSpace50(View view) {boardSpaceClicked(5, 0);}
    public void boardSpace51(View view) {boardSpaceClicked(5, 1);}
    public void boardSpace52(View view) {boardSpaceClicked(5, 2);}
    public void boardSpace53(View view) {boardSpaceClicked(5, 3);}
    public void boardSpace54(View view) {boardSpaceClicked(5, 4);}
    public void boardSpace55(View view) {boardSpaceClicked(5, 5);}
    public void boardSpace56(View view) {boardSpaceClicked(5, 6);}
    public void boardSpace57(View view) {boardSpaceClicked(5, 7);}

    public void boardSpace60(View view) {boardSpaceClicked(6, 0);}
    public void boardSpace61(View view) {boardSpaceClicked(6, 1);}
    public void boardSpace62(View view) {boardSpaceClicked(6, 2);}
    public void boardSpace63(View view) {boardSpaceClicked(6, 3);}
    public void boardSpace64(View view) {boardSpaceClicked(6, 4);}
    public void boardSpace65(View view) {boardSpaceClicked(6, 5);}
    public void boardSpace66(View view) {boardSpaceClicked(6, 6);}
    public void boardSpace67(View view) {boardSpaceClicked(6, 7);}

    public void boardSpace70(View view) {boardSpaceClicked(7, 0);}
    public void boardSpace71(View view) {boardSpaceClicked(7, 1);}
    public void boardSpace72(View view) {boardSpaceClicked(7, 2);}
    public void boardSpace73(View view) {boardSpaceClicked(7, 3);}
    public void boardSpace74(View view) {boardSpaceClicked(7, 4);}
    public void boardSpace75(View view) {boardSpaceClicked(7, 5);}
    public void boardSpace76(View view) {boardSpaceClicked(7, 6);}
    public void boardSpace77(View view) {boardSpaceClicked(7, 7);}

    //----------------------------------------------------------------------------------------------

    //to swap between layouts
    private View connectScreen;
    private View activityNewGame;

    // to start Bluetooth, discover and pair
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    private DeviceListAdapter mDeviceListAdapter;
    private ListView lvNewDevices;
    private Snackbar message;

    // to connect to another device and send data
    private BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private BluetoothDevice mBTDevice;

    private BroadcastReceiver mBroadcastReceiverDiscover = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    private BroadcastReceiver mBroadcastReceiverPairing = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                View parentLayout = findViewById(android.R.id.content);
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    message = Snackbar.make(parentLayout, "Pairing successful.", Snackbar.LENGTH_SHORT);
                    message.show();
                    mBTDevice = mDevice;
                }
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    message = Snackbar.make(parentLayout, "Pairing with selected device. Please wait.", Snackbar.LENGTH_SHORT);
                    message.show();
                }
            }
        }
    };

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            try {
                if (action.equals("SwitchScreen")) {
                    activityNewGame.setVisibility(View.VISIBLE);
                    connectScreen.setVisibility(View.GONE);
                }
                if (action.equals("GameLost")) {
                    // TODO: Display AlertDialog "Verloren!" and button "Schließen"
                    new AlertDialog.Builder(NewGameActivity.this)
                            .setMessage("Verloren!")
                            .setNegativeButton("Schließen", null)
                            .create().show();
                }
                if (action.equals("GameDraw")) {
                    // TODO: Display AlertDialog "Unentschieden" and button "Schließen"
                    new AlertDialog.Builder(NewGameActivity.this)
                            .setMessage("Patt!")
                            .setNegativeButton("Schließen", null)
                            .create().show();
                }
                if (action.equals("OfferDraw")) {
                    // TODO: Display AlertDialog "Gegner bietet ein Remi an." and buttons "Akzeptieren", "Ablehnen"
                    //  if "Akzeptieren": Send offer Draw reply, Display Alert Dialog "Unentschieden" and buttons "Neues Spiel", "Hauptmenü"
                    new AlertDialog.Builder(NewGameActivity.this)
                            .setMessage("Gegner bietet ein Remi an.")
                            .setPositiveButton("Akzeptieren", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int dialogInt) {
                                    String offerDrawReply = "OfferDrawReply";
                                    byte[] bytes = offerDrawReply.getBytes(Charset.defaultCharset());
                                    mBluetoothConnection.write(bytes);
                                    new AlertDialog.Builder(NewGameActivity.this)
                                            .setMessage("Unentschieden!")
                                            .setPositiveButton("Neues Spiel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int dialogInt) {
                                                    chessGame = new ChessGame();
                                                    byte[] data = SerializationUtils.serialize(chessGame);
                                                    mBluetoothConnection.write(data);
                                                    updateBoard();
                                                }
                                            })
                                            .setNegativeButton("Zurück zum Hauptmenü", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int dialogInt) {
                                                    finish();
                                                }
                                            })
                                            .setCancelable(false)
                                            .create().show();
                                }
                            })
                            .setNegativeButton("Ablehnen", null)
                            .create().show();
                }
                if (action.equals("OfferDrawReply")) {
                    // TODO: Display AlertDialog "Unentschieden! Gegner hat Remi akzeptiert." and button "Schließen"
                    new AlertDialog.Builder(NewGameActivity.this)
                            .setMessage("Unentschieden!" + System.lineSeparator() + "Gegner hat Remi akzeptiert.")
                            .setNegativeButton("Schließen", null)
                            .create().show();
                }
                if (action.equals("Forfeit")) {
                    // TODO: Display AlertDialog "Gewonnen! Gegner hat aufgegeben." and button "Schlißen"
                    new AlertDialog.Builder(NewGameActivity.this)
                            .setMessage("Gewonnen!" + System.lineSeparator() + "Gegner hat aufgegeben.")
                            .setNegativeButton("Schließen", null)
                            .create().show();
                }
                if (action.equals("MainMenu")) {
                    // TODO: Display AlertDialog "Mitspieler hat das Spiel verlassen." and button "Hauptmenü"
                    new AlertDialog.Builder(NewGameActivity.this)
                            .setMessage("Mitspieler hat das Spiel verlassen.")
                            .setPositiveButton("Zurück zum Hauptmenü", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int dialogInt) {
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .create().show();
                }
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
            if (action.equals("GameState")){
                try {
                    chessGame = SerializationUtils.deserialize(intent.getByteArrayExtra("currentState"));
                } catch (Exception e) {e.printStackTrace();}
                updateBoard();
            }
        }
    };

    @Override
    protected void onDestroy() {
        //TODO: send message to other device to return to main menu
        super.onDestroy();
        try {
            String mainMenu = "MainMenu";
            byte[] bytes = mainMenu.getBytes(Charset.defaultCharset());
            mBluetoothConnection.write(bytes);
        } catch (Exception e) {e.printStackTrace();}
        mBluetoothConnection.cancel();
        try {
            unregisterReceiver(mBroadcastReceiverDiscover);
        } catch (Exception e) {e.printStackTrace();}
        try {
            unregisterReceiver(mBroadcastReceiverPairing);
        } catch (Exception e) {e.printStackTrace();}
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectScreen = getLayoutInflater().inflate(R.layout.connect_screen, null, false);
        activityNewGame = getLayoutInflater().inflate(R.layout.activity_new_game, null, false);
        setContentView(connectScreen);
        addContentView(activityNewGame, new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        activityNewGame.setVisibility(View.GONE);

        // for device discovery
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices1);
        mBTDevices = new ArrayList<>();

        // for pairing
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiverPairing, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(NewGameActivity.this);

        //set receiver to switch to game screen after connection has been established
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("SwitchScreen"));

        // set chessgame
        chessGame = new ChessGame();
        // connect board spaces to their corresponding ImageView
        bs00=(ImageView)findViewById(R.id.bs00);bs01=(ImageView)findViewById(R.id.bs01);bs02=(ImageView)findViewById(R.id.bs02);bs03=(ImageView)findViewById(R.id.bs03);
        bs04=(ImageView)findViewById(R.id.bs04);bs05=(ImageView)findViewById(R.id.bs05);bs06=(ImageView)findViewById(R.id.bs06);bs07=(ImageView)findViewById(R.id.bs07);
        bs10=(ImageView)findViewById(R.id.bs10);bs11=(ImageView)findViewById(R.id.bs11);bs12=(ImageView)findViewById(R.id.bs12);bs13=(ImageView)findViewById(R.id.bs13);
        bs14=(ImageView)findViewById(R.id.bs14);bs15=(ImageView)findViewById(R.id.bs15);bs16=(ImageView)findViewById(R.id.bs16);bs17=(ImageView)findViewById(R.id.bs17);
        bs20=(ImageView)findViewById(R.id.bs20);bs21=(ImageView)findViewById(R.id.bs21);bs22=(ImageView)findViewById(R.id.bs22);bs23=(ImageView)findViewById(R.id.bs23);
        bs24=(ImageView)findViewById(R.id.bs24);bs25=(ImageView)findViewById(R.id.bs25);bs26=(ImageView)findViewById(R.id.bs26);bs27=(ImageView)findViewById(R.id.bs27);
        bs30=(ImageView)findViewById(R.id.bs30);bs31=(ImageView)findViewById(R.id.bs31);bs32=(ImageView)findViewById(R.id.bs32);bs33=(ImageView)findViewById(R.id.bs33);
        bs34=(ImageView)findViewById(R.id.bs34);bs35=(ImageView)findViewById(R.id.bs35);bs36=(ImageView)findViewById(R.id.bs36);bs37=(ImageView)findViewById(R.id.bs37);
        bs40=(ImageView)findViewById(R.id.bs40);bs41=(ImageView)findViewById(R.id.bs41);bs42=(ImageView)findViewById(R.id.bs42);bs43=(ImageView)findViewById(R.id.bs43);
        bs44=(ImageView)findViewById(R.id.bs44);bs45=(ImageView)findViewById(R.id.bs45);bs46=(ImageView)findViewById(R.id.bs46);bs47=(ImageView)findViewById(R.id.bs47);
        bs50=(ImageView)findViewById(R.id.bs50);bs51=(ImageView)findViewById(R.id.bs51);bs52=(ImageView)findViewById(R.id.bs52);bs53=(ImageView)findViewById(R.id.bs53);
        bs54=(ImageView)findViewById(R.id.bs54);bs55=(ImageView)findViewById(R.id.bs55);bs56=(ImageView)findViewById(R.id.bs56);bs57=(ImageView)findViewById(R.id.bs57);
        bs60=(ImageView)findViewById(R.id.bs60);bs61=(ImageView)findViewById(R.id.bs61);bs62=(ImageView)findViewById(R.id.bs62);bs63=(ImageView)findViewById(R.id.bs63);
        bs64=(ImageView)findViewById(R.id.bs64);bs65=(ImageView)findViewById(R.id.bs65);bs66=(ImageView)findViewById(R.id.bs66);bs67=(ImageView)findViewById(R.id.bs67);
        bs70=(ImageView)findViewById(R.id.bs70);bs71=(ImageView)findViewById(R.id.bs71);bs72=(ImageView)findViewById(R.id.bs72);bs73=(ImageView)findViewById(R.id.bs73);
        bs74=(ImageView)findViewById(R.id.bs74);bs75=(ImageView)findViewById(R.id.bs75);bs76=(ImageView)findViewById(R.id.bs76);bs77=(ImageView)findViewById(R.id.bs77);

        boardSpaces = new ImageView[][]{
                {bs00, bs01, bs02, bs03, bs04, bs05, bs06, bs07},
                {bs10, bs11, bs12, bs13, bs14, bs15, bs16, bs17},
                {bs20, bs21, bs22, bs23, bs24, bs25, bs26, bs27},
                {bs30, bs31, bs32, bs33, bs34, bs35, bs36, bs37},
                {bs40, bs41, bs42, bs43, bs44, bs45, bs46, bs47},
                {bs50, bs51, bs52, bs53, bs54, bs55, bs56, bs57},
                {bs60, bs61, bs62, bs63, bs64, bs65, bs66, bs67},
                {bs70, bs71, bs72, bs73, bs74, bs75, bs76, bs77}
        };
        tvPlayerTurn = (TextView)findViewById(R.id.textViewPlayerTurn);
        updateBoard();

        // set receivers for communication handling
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("GameLost"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("GameDraw"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("OfferDraw"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("OfferDrawReply"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("Forfeit"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("MainMenu"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("GameState"));
    }

    public void buttonBluetoothOnOff(View view) {
        if (!mBluetoothAdapter.isEnabled()) {
            //Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivity(enableBTIntent);
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivity(discoverableIntent);
        }
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();

            // clear current list of discovered devices
            mBTDevices.clear();
            lvNewDevices.setAdapter(null);

            View parentLayout = findViewById(android.R.id.content);
            message = Snackbar.make(parentLayout, "Turned Bluetooth off.", Snackbar.LENGTH_SHORT);
            message.show();
        }
    }

    public void buttonBluetoothDiscover(View view) {
        if (!mBluetoothAdapter.isEnabled()) {
            View parentLayout = findViewById(android.R.id.content);
            message = Snackbar.make(parentLayout, "Can't discover while Bluetooth is disabled.", Snackbar.LENGTH_SHORT);
            message.show();
        } else {
            if (mBluetoothAdapter.isDiscovering()) {
                checkBTPermissions();
                mBluetoothAdapter.cancelDiscovery();

                // clear current list of discovered devices
                mBTDevices.clear();
                lvNewDevices.setAdapter(null);

                mBluetoothAdapter.startDiscovery();
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mBroadcastReceiverDiscover, discoverDevicesIntent);

                View parentLayout = findViewById(android.R.id.content);
                message = Snackbar.make(parentLayout, "Trying to discover devices.", Snackbar.LENGTH_SHORT);
                message.show();
            }
            if (!mBluetoothAdapter.isDiscovering()) {
                checkBTPermissions();

                // clear current list of discovered devices
                mBTDevices.clear();
                lvNewDevices.setAdapter(null);

                mBluetoothAdapter.startDiscovery();
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mBroadcastReceiverDiscover, discoverDevicesIntent);

                View parentLayout = findViewById(android.R.id.content);
                message = Snackbar.make(parentLayout, "Trying to discover devices.", Snackbar.LENGTH_SHORT);
                message.show();
            }
        }
    }

    public void buttonBluetoothConnect(View view) {
        startConnection();
    }

    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // cancel discovery to reduce load on memory
        mBluetoothAdapter.cancelDiscovery();

        // pair with clicked device
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (mBTDevices.get(i).getBondState() == BluetoothDevice.BOND_BONDED) {
                View parentLayout = findViewById(android.R.id.content);
                message = Snackbar.make(parentLayout, "Already paired with the selected device.", Snackbar.LENGTH_SHORT);
                message.show();
                mBTDevices.get(i).createBond();

                mBTDevice = mBTDevices.get(i);
                mBluetoothConnection = new BluetoothConnectionService(NewGameActivity.this);
            } else {
                mBTDevices.get(i).createBond();

                mBTDevice = mBTDevices.get(i);
                mBluetoothConnection = new BluetoothConnectionService(NewGameActivity.this);
            }
        }
    }

    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        mBluetoothConnection.startClient(device, uuid);
    }

    public void startConnection() {
        if (mBTDevice == null || mBTDevice.getBondState() == BluetoothDevice.BOND_NONE) {
            View parentLayout = findViewById(android.R.id.content);
            message = Snackbar.make(parentLayout, "You need to select a device before you can connect.", Snackbar.LENGTH_SHORT);
            message.show();
        } else {
            isPlayerHost = true;
            startBTConnection(mBTDevice, MY_UUID_INSECURE);
        }
    }

}