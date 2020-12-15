package com.androidclass.harmonyhost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.SpotifyService;
import retrofit.client.Response;


public class Playlist extends AppCompatActivity {

    private static final String TAG = "Playlist";
    public static SpotifyService spotifyService;

    private static final String CLIENT_ID = "b47aaa91fece4e66897e50cb0ffce5ab";
    private static final String REDIRECT_URI = "harmonyhost://callback";
    private static final int REQUEST_CODE = 1337;
    private SpotifyAppRemote mSpotifyAppRemote;
    ;

    private static final String PLAYLIST_URI = "spotify:playlist:37i9dQZF1DX6bnzK9KPvrz";
    private TextView tvMusicpLaying1;
    private TextView tvMusicpLaying2;
    private TextView tvMusicpLaying3;
    private TextView tvMusicpLaying4;
    private TextView tvMusicpLaying5;
    private TextView tvMusicPlaying6;
    private TextView tvMusicPlaying7;
    private TextView tvMusicPlaying8;

    private Button btnMusicPlaying1;
    private Button btnMusicPlaying2;
    private Button btnMusicPlaying3;
    private Button btnMusicPlaying4;
    private Button btnMusicPlaying5;
    private Button btnMusicPlaying6;
    private Button btnMusicPlaying7;
    private Button btnMusicPlaying8;

    private ImageButton btnBackAccount;




    private Button btnPrevSong;
    private Button btnNextSong;
    private Button btnPlay;
    private Button btnResume;
    private Button btnPause;

    private TextView tvCurrentSong;


    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        tvMusicpLaying1 = findViewById(R.id.tvMusicPlaying1);
        tvMusicpLaying2 = findViewById(R.id.tvMusicPlaying2);
        tvMusicpLaying3 = findViewById(R.id.tvMusicPlaying3);
        tvMusicpLaying4 = findViewById(R.id.tvMusicPlaying4);
        tvMusicpLaying5 = findViewById(R.id.tvMusicPlaying5);
        tvMusicPlaying6 = findViewById(R.id.tvMusicPlaying6);
        tvMusicPlaying7 = findViewById(R.id.tvMusicPlaying7);
        tvMusicPlaying8 = findViewById(R.id.tvMusicPlaying8);
        tvCurrentSong = findViewById(R.id.tvCurrentSong);

        btnMusicPlaying1 = findViewById(R.id.btnMusicPlaying1);
        btnMusicPlaying2 = findViewById(R.id.btnMusicPlaying2);
        btnMusicPlaying3 = findViewById(R.id.btnMusicPlaying3);
        btnMusicPlaying4 = findViewById(R.id.btnMusicPlaying4);
        btnMusicPlaying5 = findViewById(R.id.btnMusicPlaying5);
        btnMusicPlaying6 = findViewById(R.id.btnMusicPlaying6);
        btnMusicPlaying7 = findViewById(R.id.btnMusicPlaying7);
        btnMusicPlaying8 = findViewById(R.id.btnMusicPlaying8);

        btnBackAccount = findViewById(R.id.btnBackAccount);


        btnNextSong = findViewById(R.id.btnNextSong);
        btnPrevSong = findViewById(R.id.btnPrevSong);
        btnPlay = findViewById(R.id.btnPlay);
        btnResume = findViewById(R.id.btnResume);
        btnPause = findViewById(R.id.btnPause);


        setServiceAPI();
        getMyPlayList();

        btnBackAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intBackAccount = new Intent(Playlist.this, AccountPage.class);
                startActivity(intBackAccount);
            }
        });


        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-public", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        //onPlayPlaylistButtonClicked();

        btnMusicPlaying1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track = playerState.track;
                            if (track != null) {
                                Log.d("Playlist", track.name + " by " + track.artist.name);
                                tvMusicpLaying1.setText(track.name);
                            }
                        });
            }
        });



        btnMusicPlaying2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track2 = playerState.track;
                            if (track2 != null) {
                                Log.d("Playlist", track2.name + " by " + track2.artist.name);
                                tvMusicpLaying2.setText(track2.name);
                            }
                        });
            }
        });


        btnMusicPlaying3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track3 = playerState.track;
                            if (track3 != null) {
                                Log.d("Playlist", track3.name + " by " + track3.artist.name);
                                tvMusicpLaying3.setText(track3.name);
                            }
                        });
            }
        });



        btnMusicPlaying4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track4 = playerState.track;
                            if (track4 != null) {
                                Log.d("Playlist", track4.name + " by " + track4.artist.name);
                                tvMusicpLaying4.setText(track4.name);
                            }
                        });
            }
        });



        btnMusicPlaying5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track5 = playerState.track;
                            if (track5 != null) {
                                Log.d("Playlist", track5.name + " by " + track5.artist.name);
                                tvMusicpLaying5.setText(track5.name);
                            }
                        });
            }
        });


        btnMusicPlaying6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track6 = playerState.track;
                            if (track6 != null) {
                                Log.d("Playlist", track6.name + " by " + track6.artist.name);
                                tvMusicPlaying6.setText(track6.name);
                            }
                        });
            }
        });



        btnMusicPlaying7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track7 = playerState.track;
                            if (track7 != null) {
                                Log.d("MainActivity2", track7.name + " by " + track7.artist.name);
                                tvMusicPlaying7.setText(track7.name);
                            }
                        });
            }
        });



        btnMusicPlaying8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track8 = playerState.track;
                            if (track8 != null) {
                                Log.d("MainActivity2", track8.name + " by " + track8.artist.name);
                                tvMusicPlaying8.setText(track8.name);
                            }
                        });
            }
        });



        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX6bnzK9KPvrz");
                mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState()
                        .setEventCallback(playerState -> {
                            final Track track = playerState.track;
                            if (track != null) {
                                Log.d("Playlist", track.name + " by " + track.artist.name);
                                tvCurrentSong.setText(track.name);
                            }
                        });
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpotifyAppRemote.getPlayerApi().pause();
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpotifyAppRemote.getPlayerApi().resume();
            }
        });

        btnNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpotifyAppRemote.getPlayerApi().skipNext();
                mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState()
                        .setEventCallback(playerState -> {
                            final Track track = playerState.track;
                            if (track != null) {
                                Log.d("Playlist", track.name + " by " + track.artist.name);
                                tvCurrentSong.setText(track.name);
                            }
                        });
            }
        });

        btnPrevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpotifyAppRemote.getPlayerApi().skipPrevious();
                mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState()
                        .setEventCallback(playerState -> {
                            final Track track = playerState.track;
                            if (track != null) {
                                Log.d("Playlist", track.name + " by " + track.artist.name);
                                tvCurrentSong.setText(track.name);
                            }
                        });
            }
        });
    }

    public void getMyPlayList(){
        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.LIMIT, 30);

        spotifyService.getMyPlaylists(options, new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d("SearchPager", spotifyError.toString());
            }

            @Override
            public void success(Pager<PlaylistSimple> playlistSimplePager, Response response) {
                List<PlaylistSimple> simples = playlistSimplePager.items;

                for(PlaylistSimple simple : simples){
                    Log.d("SearchPager", simple.name);
                    Log.d("SearchPager", simple.images.get(1).url);
                }

            }
        });
    }

    private void setServiceAPI(){
        Log.d(TAG, "Setting Spotify API Service");
        SpotifyApi api = new SpotifyApi();
        spotifyService = api.getService();
    }




    public void onPlayPlaylistButtonClicked() {
        playUri(PLAYLIST_URI);

    }

    private void playUri(String uri) {
        mSpotifyAppRemote
                .getPlayerApi().
                play(uri);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // We will start writing our code here.
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;

                        Log.d("Playlist", "Connected! Yay!");

                        // Now you can start interacting with App Remote

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("Playlist", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

            private void connected() {
        // Then we will write some more code here.
        // Play a playlist



        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(new Subscription.EventCallback<PlayerState>() {
                    @Override
                    public void onEvent(PlayerState playerState) {
                        PlayerContext playerContext = new PlayerContext();
                        final Track track = playerState.track;
                        if (track != null) {
                            Log.d("Playlist", track.name + " by " + track.artist.name);
                            tvCurrentSong.setText(track.name);




                        }
                    }
                });
    }


    private void resume(){
        mSpotifyAppRemote.getPlayerApi().resume();
    }

    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSpotifyAppRemote.getPlayerApi().pause();
    }

     */



        }




