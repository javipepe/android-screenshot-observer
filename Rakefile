require './test/tester.rb'

task :default => [:validate]

task :validate do
  begin
    ruby "tester.rb"
  rescue Exception => e
    raise 'Error could not run file'
  end
end
