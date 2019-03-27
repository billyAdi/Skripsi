<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Contact extends CI_Controller {

	public function index()
	{
		$this->load->helper('string');
		$this->load->config('piktora');
		if($this->input->method()==='post'){
			$expiration = time() - $this->config->item('captcha-settings')['expiration'];
			$this->db->where('captcha_time < ', $expiration)->delete('captcha');
			$this->db->where('word', strtolower($this->input->post('captcha')));
			$this->db->where('ip_address', $this->input->ip_address());
			$this->db->where('captcha_time >',$expiration);
			$query = $this->db->get('captcha');
			if ($query->num_rows() === 0)
			{
				$this->session->set_flashdata('warning', "Sorry. Wrong Captcha.");
			}
			else{
				$this->load->model("Email_model");
				$name = $this->input->post('name');
				$email = $this->input->post('email');
				$subject = $this->input->post('subject');
				$tempMessage = $this->input->post('message');
				$message = "<p>Name: $name</p><p>Email: $email</p><p>Message: $tempMessage</p><p>*note: jika ingin membalas email, balas dengan email tujuan yang tertera di pesan (tidak melalui reply)</p>";
				$this->Email_model->send_email($email, $name, $subject, $message);
				$this->session->set_flashdata('success', "Thanks for your response.");
			}
			redirect('/contact', 'refresh');
		}
		else{
			$this->load->helper('captcha');
			$cap = create_captcha($this->config->item('captcha-settings'));
			$data = array(
				'captcha_time'  => $cap['time'],
				'ip_address'    => $this->input->ip_address(),
				'word'          => strtolower($cap['word'])
				);
			$query = $this->db->insert_string('captcha', $data);
			$this->db->query($query);
			$this->load->view('contact', array('captcha'=>$cap['image']));	
		}
		
	}
}
