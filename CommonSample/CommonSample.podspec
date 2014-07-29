Pod::Spec.new do |s|
  s.name         = "CommonSample"
  s.version      = "0.0.1"
  s.summary      = "A short description of CommonSample."
  s.description  = <<-DESC
                   A longer description of CommonSample in Markdown format.

                   * Think: Why did you write this? What is the focus? What does it do?
                   * CocoaPods will be using this to generate tags, and improve search results.
                   * Try to keep it short, snappy and to the point.
                   * Finally, don't worry about the indent, CocoaPods strips it!
                   DESC
  s.homepage     = "http://EXAMPLE/CommonSample"
  s.license      = "MIT"
  s.author             = { "" => "" }
  s.platform     = :ios, "6.0"
  #s.source       = { :git => "http://EXAMPLE/CommonSample.git", :tag => "0.0.1" }
  s.source_files  = "CommonSample", "CommonSample/**/*.{h,m}"
  s.public_header_files = "CommonSample/**/*.h"
  s.frameworks = "Foundation", "XCTest"
  s.requires_arc = true
  s.dependency "FMDB"

end
