<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Configuration_model extends CI_Model {

    public function set($id, $value) {
        $this->db->set('value', $value);
        $this->db->where('id', $id);
        $this->db->update('configuration');
    }
    
    public function get($id) {
        $query = $this->db->get_where('configuration', array('id' => $id));
        $data = $query->row();
        if ($data !== NULL) {
            return $data->value;
        }
        return NULL;
    }

    public function getCSV($id) {
        return preg_split('/ *, */', $this->get($id));
    }
}