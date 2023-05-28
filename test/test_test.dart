/* import 'package:flutter_test/flutter_test.dart';
import 'package:test/test.dart';
import 'package:test/test_platform_interface.dart';
import 'package:test/test_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockTestPlatform
    with MockPlatformInterfaceMixin
    implements TestPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
  
  @override
  Future<bool?> getIsBiometricSupport() {
    // TODO: implement getIsBiometricSupport
    throw UnimplementedError();
  }
}

void main() {
  final TestPlatform initialPlatform = TestPlatform.instance;

  test('$MethodChannelTest is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTest>());
  });

  test('getPlatformVersion', () async {
    Test testPlugin = Test();
    MockTestPlatform fakePlatform = MockTestPlatform();
    TestPlatform.instance = fakePlatform;

    expect(await testPlugin.getPlatformVersion(), '42');
  });
}
 */