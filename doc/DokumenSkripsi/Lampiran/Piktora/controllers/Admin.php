<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Admin extends CI_Controller {

    public function __construct() {
        parent::__construct();
        $this->load->model('Auth_model');
        $this->load->library('session');
    }

    public function index() {
        $authURL = $this->Auth_model->createAuthURL();
        $this->load->view('admin/welcome', array('authURL' => $authURL));
    }

    public function oauth2callback() {
        try {
            $code = $this->input->get('code');
            if ($code !== NULL) {
                $this->Auth_model->authenticateOauthCode($code);
                $userInfo = $this->Auth_model->getUserInfo();
                $this->session->set_flashdata('info', 'Selamat datang, ' . $userInfo['name'] . '!');
                header('Location: /admin/dashboard');
            } else {
                throw new Exception("Mohon login terlebih dahulu.");
            }
        } catch (Exception $ex) {
            $this->session->set_flashdata('error', $ex->getMessage());
            header("Location: /admin");
            return;
        }
    }

    public function dashboard($subpage = NULL) {
        $this->ensureAdminAccess();
        switch ($subpage) {
            case NULL:
                if ($this->input->method() === 'post') {
                    foreach ($this->input->post() as $id => $value) {
                        $this->db->set('value', $value);
                        $this->db->where('id', $id);
                        $this->db->update('configuration');
                    }
                    $this->session->set_flashdata('info', 'Data konfigurasi berhasil diperbaharui');
                    header("Location: /admin/dashboard");
                } else {
                    $this->db->select('id, name, inputAttributes, value');
                    $query = $this->db->get('configuration');
                    $configurations = $query->result();
                    $this->load->view('admin/dashboard', array(
                        'configurations' => $configurations
                    ));
                }
                break;
            default:
                show_404();
        }
    }

    public function projects($subpage = NULL, $id = NULL) {
        $this->ensureAdminAccess();
        switch ($subpage) {
            case NULL:
                $this->db->where('title', NULL);
                $this->db->delete('projects', array('title' => NULL));
                $this->db->order_by('id', 'ASC');
                $query = $this->db->get('projects');
                $projects = $query->result();
                $data = array(
                    'projects' => $projects
                );
                $this->load->view('admin/projects', $data);
                break;
            case 'edit':
                $query = $this->db->get_where('projects', array('id' => $id));
                $project = $query->row();
                if ($project === FALSE) {
                    show_404();
                    exit();
                }
                if (file_exists("assets/img/projects/$id.jpg")) {
                    $project->mainphoto = "/assets/img/projects/$id.jpg";
                } else {
                    $project->mainphoto = NULL;
                }
                $project->photos = [];
                for ($i = 1; file_exists("assets/img/projects/$id-$i.jpg"); $i++) {
                    $project->photos[] = "/assets/img/projects/$id-$i.jpg";
                }
                $data = array(
                    'project' => $project
                );
                $this->load->view('admin/project_edit', $data);
                break;
            case 'update':
                if ($this->input->method() !== 'post') {
                    show_404();
                    exit();
                }
                try {
                    $data = array(
                        'title' => $this->input->post('title'),
                        'products' => $this->input->post('products'),
                        'categories' => $this->input->post('categories'),
                        'aboutClient' => $this->input->post('aboutClient'),
                        'ourConcept' => $this->input->post('ourConcept'),
                        'additionalInfo' => $this->input->post('additionalInfo')
                    );
                    $this->db->where('id', $id);
                    $this->db->update('projects', $data);
                    $this->session->set_flashdata('info', 'Berhasil melakukan update project');
                } catch (Exception $e) {
                    $this->session->set_flashdata('error', $e->getMessage());
                }
                header("Location: /admin/projects");
                break;
            case 'add':
                if ($this->input->method() !== 'post') {
                    show_404();
                    exit();
                }
                try {
                    $this->db->insert('projects', array(
                        'title' => NULL,
                    ));
                    $id = $this->db->insert_id();
                    $this->session->set_flashdata('info', 'Berhasil menambah project, silahkan edit di halaman ini.');
                    header("Location: /admin/projects/edit/$id");
                } catch (Exception $e) {
                    $this->session->set_flashdata('error', $e->getMessage());
                    header("Location: /admin/projects");
                }
                break;
            case 'upload':
                if ($this->input->method() !== 'post') {
                    show_404();
                    exit();
                }
                try {
                    $id = $this->input->post('id');
                    $type = $this->input->post('type');
                    switch ($type) {
                        case 'main':
                            $config = array(
                                'allowed_types' => 'jpg',
                                'max_size' => 1024,
                                'overwrite' => TRUE,
                                'upload_path' => "assets/img/projects",
                                'file_name' => "$id.jpg"
                            );
                            $this->load->library('upload', $config);
                            if (!$this->upload->do_upload('file')) {
                                throw new Exception($this->upload->display_errors('', ''));
                            }
                            $this->session->set_flashdata('info', 'Berhasil upload foto utama');
                            break;
                        case 'additional':
                            $maxPhotos = 9;
                            $config = array(
                                'allowed_types' => 'jpg',
                                'max_size' => 1024,
                                'overwrite' => TRUE,
                                'upload_path' => "assets/img/projects"
                            );
                            for ($i=1; $i <=$maxPhotos ; $i++) { 
                               if (!file_exists("assets/img/projects/$id-$i.jpg")) {
                                break;
                                } 
                            }
                            $config['file_name'] = "$id-$i.jpg";
                            $this->load->library('upload', $config);
                            if (!$this->upload->do_upload('file')) {
                                throw new Exception($this->upload->display_errors('', ''));
                            }
                            $this->session->set_flashdata('info', 'Berhasil upload foto tambahan');
                            break;
                        default:
                            show_404();
                            break;
                    }
                    header("Location: /admin/projects/edit/$id");
                } catch (Exception $e) {
                    $this->session->set_flashdata('error', $e->getMessage());
                    header("Location: /admin/projects/edit/$id");
                }
                break;
            case 'remove':
                try {
                    if ($this->input->method() !== 'get') {
                        show_404();
                        exit();
                    }
                    $type = $this->input->get('type');
                    $additionalid = $this->input->get('additionalid');
                    switch($type){
                        case 'project':
                            $this->db->delete('projects', array('id' => $id));
                            $this->session->set_flashdata('info', 'Berhasil menghapus project');
                            header("Location: /admin/projects");
                            break;
                        case 'mainphoto':
                            unlink("assets/img/projects/$id.jpg");
                            $this->session->set_flashdata('info', 'Berhasil menghapus foto utama');
                            header("Location: /admin/projects/edit/$id");
                            break;
                        case 'additionalphoto':
                            for ($i=$additionalid; $i <=10 ; $i++) {
                                $next = $i+1;
                                if (!file_exists("assets/img/projects/$id-$next.jpg")) {
                                    unlink("assets/img/projects/$id-$i.jpg");
                                    break;
                                }
                                copy("assets/img/projects/$id-$next.jpg", "assets/img/projects/$id-$i.jpg");
                            }
                            $this->session->set_flashdata('info', 'Berhasil menghapus foto tambahan');
                            header("Location: /admin/projects/edit/$id");
                            break;
                        default:
                            show_404();
                            break;
                    }
                } catch (Exception $e) {
                    $this->session->set_flashdata('error', $e->getMessage());
                    header("Location: /admin/projects");
                }
                break;
            default:
                show_404();
                break;
        }
    }
    
    public function home($subpage=null, $id=null){
        $this->ensureAdminAccess();
        switch ($subpage) {
            case null:
                $photos = [];
                for ($i = 1; file_exists("assets/img/home/$i.jpg"); $i++) {
                    $photos[] = "/assets/img/home/$i.jpg";
                }
                $this->load->view('admin/home', array("photos" => $photos));
                break;
            case "remove":
                for ($i=$id; $i <=8 ; $i++) {
                    $next = $i+1;
                    if (!file_exists("assets/img/home/$next.jpg")) {
                        unlink("assets/img/home/$i.jpg");
                        break;
                    }
                    copy("assets/img/home/$next.jpg", "assets/img/home/$i.jpg");
                }
                $this->session->set_flashdata('info', 'Berhasil menghapus foto home');
                header("Location: /admin/home");
                break;
            case "upload":
                $config = array(
                    'allowed_types' => 'jpg',
                    'max_size' => 1024,
                    'overwrite' => TRUE,
                    'upload_path' => "assets/img/home"
                    );
                for ($i=1; $i <= 8 ; $i++) { 
                    if (!file_exists("assets/img/home/$i.jpg")) {
                        break;
                    } 
                }
                echo $i;
                $config['file_name'] = "$i.jpg";
                $this->load->library('upload', $config);
                if (!$this->upload->do_upload('file')) {
                    throw new Exception($this->upload->display_errors('', ''));
                }
                $this->session->set_flashdata('info', 'Berhasil upload foto home');
                header("Location: /admin/home");
                break;
            default:
                show_404();
                break;
        }
    }   
     
    public function logout() {
        $this->Auth_model->logout();
        $this->session->set_flashdata('info', 'Anda berhasil logout');
        header("Location: /admin");
    }

    private function ensureAdminAccess() {
        $userInfo = $this->Auth_model->getUserInfo();
        if ($userInfo === NULL) {
            $this->session->set_flashdata('error', 'Anda harus login terlebih dahulu!');
            header("Location: /admin");
            exit();
        }
    }
    
}
