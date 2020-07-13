# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

  require 'yaml'
  current_dir    = File.dirname(File.expand_path(__FILE__))
  configs        = YAML.load_file("#{current_dir}/config.yml")
  vagrant_config = configs['configs']

  config.vm.synced_folder "./", "/vagrant", owner: "vagrant", mount_options: ["dmode=775,fmode=600"]

    #Controler VM
    config.vm.define "controller" do |controller|
        controller.vm.hostname = vagrant_config['sbt']['hostname']
        controller.vm.box = vagrant_config['sbt']['box']

        controller.vm.provider :virtualbox do |v|
            v.memory = vagrant_config['sbt']['ram']
            v.cpus = vagrant_config['sbt']['cpu']
            v.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
            v.customize ["modifyvm", :id, "--ioapic", "on"]
         end

        controller.vm.network :private_network, ip: vagrant_config['sbt']['ip']
	controller.vm.network :forwarded_port, guest: 8080, host: 8080
	controller.vm.network :forwarded_port, guest: 8001, host: 8001

        controller.vm.provision "ansible_local" do |ansible|
          ansible.galaxy_role_file = "requirements.yml"
          ansible.playbook = "setup.yml"
          ansible.verbose = true
          ansible.install = true
          ansible.limit = "all"
          ansible.provisioning_path = "/vagrant/provisioning"
          #ansible.inventory_path = "hosts"
        end
      end

end
