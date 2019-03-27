<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Projects extends CI_Controller {

	public function index()
	{	
		$query = $this->db->get_where('projects', array(
			'title IS NOT NULL' => null
			));
		$this->load->view('projects', array('projects' => $query->result_array()));
	}

	public function detail($id)
	{
		$query = $this->db->get_where('projects', array(
				'id' => $id
			));
		$project = $query->row_array();
		$photos = [];
		for ($i = 1; file_exists("assets/img/projects/$id-$i.jpg"); $i++) {
			$photos[] = "/assets/img/projects/$id-$i.jpg";
		}
		$project['photos'] = $photos;
		$this->load->view('project_details', $project);
	}
}
