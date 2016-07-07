module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    clean: ["src/main/webapp/build/js","src/main/webapp/build/css","src/main/webapp/build/views"],
    uglify: {
      options: {
        banner: '/*! <%= pkg.name %>-<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %> */\n'
      },
      build: {
        files: [{
                    expand:true,
                    cwd:'src/main/webapp/js',//js目录下
                    src:'**/*.js',//所有js文件
                    dest: 'src/main/webapp/build/js',//输出到此目录下
                    ext: '-<%= pkg.version %>.min.js'
                }]
      }
    },
    cssmin: {
	  options: {                                 // Target options 
	      advanced :false
	  },
      target: {
        files: [{
          expand: true,
          cwd: 'src/main/webapp/css',
          src: '**/*.css',
          dest: 'src/main/webapp/build/css',
          ext: '-<%= pkg.version %>.min.css'
        }]
      }
    },
    htmlmin: {                                     // Task 
      build: {                                      // Target 
        options: {                                 // Target options 
          removeComments: true,
          collapseWhitespace: true,
          keepClosingSlash:true,
          caseSensitive:true,
          preventAttributesEscaping:true
        },
        files: [{
          expand: true,
          cwd: 'src/main/webapp/views',
          src: ['**/*.{jsp,htm,html}'],
          dest: 'src/main/webapp/build/views'
        }]
      }
    },
    usemin: {
      html: ['src/main/webapp/build/views/*.{jsp.htm,html}']
    }

  });

  grunt.loadNpmTasks('grunt-contrib-clean');
  // 加载包含 "uglify" 任务的插件。
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-htmlmin');
  grunt.loadNpmTasks('grunt-usemin');

  // 默认被执行的任务列表。
  //grunt.registerTask('build', ['clean','uglify','cssmin','copy','usemin']);
  grunt.registerTask('build', ['clean','uglify','cssmin']);//,'htmlmin'

};