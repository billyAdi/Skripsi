<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Auth_model extends CI_Model {

    private $client;

    public function __construct() {
        parent::__construct();

        $this->load->config('auth');
        $this->client = new Google_Client();
        $this->client->setHttpClient(new GuzzleHttp\Client(array(
//            'base_uri' => Google_Client::API_BASE_PATH, // if doesn't work, try to uncomment this.
            'verify' => getcwd() . '/../certs/cacert.pem'
        )));
        $this->client->setClientId($this->config->item('google-clientid'));
        $this->client->setClientSecret($this->config->item('google-clientsecret'));
        $this->client->setRedirectUri($this->config->item('google-redirecturi'));
        $this->client->addScope('https://www.googleapis.com/auth/userinfo.email');
        $this->client->addScope('https://www.googleapis.com/auth/userinfo.profile');        
    }

    /**
     * Panggil method ini untuk mendapatkan hyperlink untuk melakukan OAuth.
     * Biasanya digunakan saat akan menampilkan halaman "Login with Google".
     * @return string URL untuk login
     */
    public function createAuthURL() {
        return $this->client->createAuthUrl();
    }

    /**
     * Panggil ini untuk menerima kode autentikasi hasil redirect dari Google,
     * dan menentukan email dan role user yang berhasil login.
     * @param string $oauthCode kode oauth, didapat dari parameter GET "code".
     * @return boolean TRUE selalu.
     * @throws Exception jika autentikasi gagal (ditolak). Exception message
     * berisi penjelasan kenapa.
     */
    public function authenticateOauthCode($oauthCode) {
        $this->client->authenticate($oauthCode);
        $oauth2Service = new Google_Service_Oauth2($this->client);
        $userinfo = $oauth2Service->userinfo->get();
        $email = $userinfo['email'];
        $name = $userinfo['name'];

        $this->load->model('Configuration_model');
        $allowedUsers = $this->Configuration_model->getCSV('administrator_emails');
        if (in_array($email, $allowedUsers)) {
            $this->session->set_userdata('auth', array(
                'email' => $email,
                'name' => $name,
            ));
        } else {
            throw new Exception("$email ($name) does not have access to admin page!");
        }
    }

    public function getUserInfo() {
        return $this->session->userdata('auth');
    }

    public function logout() {
        $this->session->unset_userdata('auth');
    }

}
