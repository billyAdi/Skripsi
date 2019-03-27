<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {
	
	public function index()
	{
		$photos = [];
		for ($i = 1; file_exists("assets/img/home/$i.jpg"); $i++) {
			$photos[] = "/assets/img/home/$i.jpg";
		}
		$this->load->view('welcome', array('photos'=>$photos));
	}
}
