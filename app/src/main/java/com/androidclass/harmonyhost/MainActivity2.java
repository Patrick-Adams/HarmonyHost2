package com.androidclass.harmonyhost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.annotations.SerializedName;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.UserApi;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.LibraryState;
import com.spotify.protocol.types.ListItems;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.PlayerOptions;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;


public class MainActivity2 extends AppCompatActivity {

    private static final String CLIENT_ID = "b47aaa91fece4e66897e50cb0ffce5ab";
    private static final String REDIRECT_URI = "harmonyhost://callback";
    private static final int REQUEST_CODE = 1337;
    private SpotifyAppRemote mSpotifyAppRemote;
    private Button btnBackMusic;
    private Button btnBackMusicPause;
    private Button btnBackMusicResume;
    private Button btnPlaylist;
    private Button btnNext;
    private Button btnPrev;
    private TextView currentSong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnBackMusic = findViewById(R.id.btnBackMusic);
        btnBackMusicPause =  findViewById(R.id.btnBackMusicPause);
        btnBackMusicResume =  findViewById(R.id.btnBackMusicResume);
        btnPlaylist = findViewById(R.id.btnPlaylist);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        currentSong = findViewById(R.id.tvMusicPlaying);



        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-public", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);


        btnBackMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpotifyAppRemote.getPlayerApi().setShuffle(false);
                mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
                connected();
            }
        });

        btnBackMusicPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });

        btnBackMusicResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resume();
            }
        });

        btnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToPlaylist = new Intent(MainActivity2.this, Playlist.class);
                startActivity(intToPlaylist);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevSong();
            }
        });

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

                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    private void connected() {
        // Then we will write some more code here.
        // Play a playlist


        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity2", track.name + " by " + track.artist.name);
                        currentSong.setText(track.name);
                    }
                });
/*
        SpotifyApi api = new SpotifyApi();
        api.setAccessToken("yourAccessToken");
        SpotifyService spotify = api.getService();

        spotify.getMyPlaylists(new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                // handle error
            }

            @Override
            public void success(Pager<PlaylistSimple> playlistSimplePager, Response response) {
                // do something
                // for example
                for(PlaylistSimple playlistSimple: playlistSimplePager.items) {
                    Log.d("playlist:", playlistSimple.name);
                }
            }
        });
        */
    }
    private void pause() {
        mSpotifyAppRemote.getPlayerApi().pause();
    };

    private void resume(){
        mSpotifyAppRemote.getPlayerApi().resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSpotifyAppRemote.getPlayerApi().pause();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void nextSong(){
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    private  void prevSong(){
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }




    @Override
    protected void onStop() {
        super.onStop();
        // Aaand we will finish off here.
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
        mSpotifyAppRemote.getPlayerApi().pause();
    }



}

